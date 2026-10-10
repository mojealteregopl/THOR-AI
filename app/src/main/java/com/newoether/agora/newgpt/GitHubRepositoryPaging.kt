package com.newoether.agora.newgpt

import java.io.IOException

/**
 * Fetches a bounded GitHub repository listing without silently dropping later pages.
 * The actual HTTP transport remains owned by GitHubConnector.
 */
internal object GitHubRepositoryPaging {
    suspend fun collect(
        pageSize: Int = 100,
        maxPages: Int = 50,
        fetchPage: suspend (page: Int) -> List<String>,
    ): List<String> {
        require(pageSize in 1..100) { "Nieprawidłowy rozmiar strony GitHub." }
        require(maxPages in 1..50) { "Nieprawidłowy limit stron GitHub." }

        val repositories = LinkedHashSet<String>()
        for (page in 1..maxPages) {
            val entries = fetchPage(page)
            require(entries.size <= pageSize) { "GitHub zwrócił zbyt dużo rekordów na stronie." }
            repositories.addAll(entries)
            if (entries.size < pageSize) return repositories.toList()
        }
        throw IOException("Przekroczono limit ${pageSize * maxPages} repozytoriów. Lista nie została ucięta bez ostrzeżenia.")
    }
}
