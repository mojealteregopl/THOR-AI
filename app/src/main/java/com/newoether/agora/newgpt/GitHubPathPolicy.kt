package com.newoether.agora.newgpt

/**
 * Validation boundary for user-supplied GitHub API path components.
 * Network code must add validated values as individual HttpUrl path/query segments.
 */
internal object GitHubPathPolicy {
    private val repositoryPattern = Regex("[A-Za-z0-9][A-Za-z0-9-]{0,38}/[A-Za-z0-9_.-]{1,100}")
    private val branchPattern = Regex("[A-Za-z0-9_-][A-Za-z0-9._/-]{0,199}")

    fun repositoryParts(repository: String): Pair<String, String> {
        val input = repository.trim()
        require(repositoryPattern.matches(input)) { "Nieprawidłowe repozytorium owner/name." }
        val parts = input.split('/')
        require(!parts[0].endsWith('-') && !parts[1].endsWith('.')) {
            "Nieprawidłowe repozytorium owner/name."
        }
        return parts[0] to parts[1]
    }

    fun filePathSegments(path: String): List<String> {
        require(path.isNotBlank() && path.length <= 1024) { "Nieprawidłowa długość ścieżki pliku." }
        require(!path.startsWith('/') && !path.endsWith('/')) { "Ścieżka musi być względna." }
        val segments = path.split('/')
        require(segments.all { segment ->
            segment.isNotEmpty() && segment != "." && segment != ".." &&
                '\\' !in segment && segment.none { it.isISOControl() }
        }) { "Ścieżka zawiera niedozwolony segment." }
        return segments
    }

    fun branchRef(branch: String): String {
        require(branchPattern.matches(branch) && !branch.endsWith('/') && !branch.endsWith('.')) {
            "Nieprawidłowa nazwa gałęzi Git."
        }
        require(!branch.contains("..") && !branch.contains("//") &&
            branch.split('/').none { it.startsWith('.') || it.endsWith(".lock") }
        ) { "Nieprawidłowa nazwa gałęzi Git." }
        return branch
    }
}
