package com.newoether.agora.newgpt

import android.content.Context
import com.newoether.agora.util.SecretCrypto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.Base64

class GitHubConnector(context: Context) {
    private val prefs = context.getSharedPreferences("newgpt_github", Context.MODE_PRIVATE)
    private val client = OkHttpClient()
    private val jsonType = "application/json".toMediaType()

    var token: String
        get() {
            val stored = prefs.getString("token", "").orEmpty()
            if (stored.isBlank()) return ""
            if (SecretCrypto.isEncrypted(stored)) return SecretCrypto.decrypt(stored)

            // Migrate existing plaintext tokens only if Keystore encryption succeeds.
            val migrated = SecretCrypto.encrypt(stored)
            if (!SecretCrypto.isEncrypted(migrated)) return ""
            prefs.edit().putString("token", migrated).apply()
            return stored
        }
        private set(value) {
            if (value.isBlank()) {
                prefs.edit().remove("token").apply()
                return
            }
            val encrypted = SecretCrypto.encrypt(value)
            check(SecretCrypto.isEncrypted(encrypted)) {
                "Nie można bezpiecznie zapisać tokenu GitHub."
            }
            prefs.edit().putString("token", encrypted).apply()
        }

    fun setToken(value: String) { token = value.trim() }
    fun clearToken() { prefs.edit().remove("token").apply() }

    suspend fun listRepositories(): Result<List<String>> = withContext(Dispatchers.IO) {
        runCatching {
            requestJson("https://api.github.com/user/repos?per_page=100&sort=updated") { body ->
                val array = JSONArray(body)
                buildList {
                    for (i in 0 until array.length()) add(array.getJSONObject(i).getString("full_name"))
                }
            }
        }
    }

    suspend fun createOrUpdateFile(
        repository: String,
        path: String,
        content: String,
        message: String,
        branch: String? = null,
    ): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val (owner, repo) = GitHubPathPolicy.repositoryParts(repository)
            val segments = GitHubPathPolicy.filePathSegments(path)
            require(message.isNotBlank()) { "Wiadomość commita jest pusta." }

            val repositoryUrl = HttpUrl.Builder()
                .scheme("https")
                .host("api.github.com")
                .addPathSegment("repos")
                .addPathSegment(owner)
                .addPathSegment(repo)
                .build()

            // Resolve the actual default branch (which may be master, main, or a different ref).
            val targetBranch = GitHubPathPolicy.branchRef(
                branch ?: requestJson(repositoryUrl.toString()) { body ->
                    JSONObject(body).getString("default_branch")
                },
            )

            val contentUrlBuilder = repositoryUrl.newBuilder().addPathSegment("contents")
            segments.forEach(contentUrlBuilder::addPathSegment)
            val contentUrl = contentUrlBuilder.build()

            val existingSha = try {
                requestJson(
                    contentUrl.newBuilder().addQueryParameter("ref", targetBranch).build().toString(),
                ) { body ->
                    JSONObject(body).getString("sha")
                }
            } catch (error: GitHubHttpException) {
                if (error.statusCode == 404) null else throw error
            }

            val encoded = Base64.getEncoder().encodeToString(content.toByteArray(Charsets.UTF_8))
            val payload = JSONObject()
                .put("message", message)
                .put("content", encoded)
                .put("branch", targetBranch)
            existingSha?.let { payload.put("sha", it) }

            requestJson(contentUrl.toString(), method = "PUT", body = payload.toString()) { response ->
                val result = JSONObject(response)
                result.optJSONObject("content")?.optString("html_url")
                    ?.takeIf(String::isNotBlank)
                    ?: result.getJSONObject("commit").getString("sha")
            }
        }
    }

    private class GitHubHttpException(val statusCode: Int, detail: String) :
        IOException("GitHub HTTP $statusCode: $detail")

    private fun <T> requestJson(
        url: String,
        method: String = "GET",
        body: String? = null,
        transform: (String) -> T,
    ): T {
        val credential = token
        check(credential.isNotBlank()) { "Najpierw skonfiguruj token GitHub." }
        val builder = Request.Builder()
            .url(url)
            .header("Accept", "application/vnd.github+json")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .header("User-Agent", "NewGPT-Android")
            .header("Authorization", "Bearer $credential")
        if (method != "GET") builder.method(method, body.orEmpty().toRequestBody(jsonType))
        client.newCall(builder.build()).execute().use { response ->
            val responseText = response.body?.string().orEmpty()
            if (!response.isSuccessful) {
                val detail = runCatching { JSONObject(responseText).optString("message") }
                    .getOrDefault("Błąd HTTP")
                    .take(300)
                throw GitHubHttpException(response.code, detail)
            }
            return transform(responseText)
        }
    }
}
