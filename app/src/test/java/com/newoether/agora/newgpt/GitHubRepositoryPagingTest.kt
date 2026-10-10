package com.newoether.agora.newgpt

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.io.IOException

class GitHubRepositoryPagingTest {
    @Test
    fun collectsPagesUntilShortPagePreservingStableOrder() = runBlocking {
        val called = mutableListOf<Int>()
        val all = GitHubRepositoryPaging.collect(pageSize = 3, maxPages = 5) { page ->
            called.add(page)
            when (page) {
                1 -> listOf("owner/A", "owner/B", "owner/C")
                2 -> listOf("owner/C", "owner/D", "owner/E")
                else -> listOf("owner/F")
            }
        }
        assertEquals(listOf(1, 2, 3), called)
        assertEquals(
            listOf("owner/A", "owner/B", "owner/C", "owner/D", "owner/E", "owner/F"),
            all,
        )
    }

    @Test
    fun emptyFirstPageCompletesWithoutExtraRequests() = runBlocking {
        var calls = 0
        val all = GitHubRepositoryPaging.collect(pageSize = 100) { page ->
            assertEquals(1, page)
            calls++
            emptyList()
        }
        assertEquals(emptyList<String>(), all)
        assertEquals(1, calls)
    }

    @Test
    fun refusesSilentTruncationAtPageLimit() {
        assertThrows(IOException::class.java) {
            runBlocking {
                GitHubRepositoryPaging.collect(pageSize = 2, maxPages = 2) {
                    listOf("owner/A", "owner/B")
                }
            }
        }
    }

    @Test
    fun rejectsOversizedPage() {
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking {
                GitHubRepositoryPaging.collect(pageSize = 2) {
                    listOf("owner/A", "owner/B", "owner/C")
                }
            }
        }
    }

    @Test
    fun validatesPageAndRequestBudgets() {
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { GitHubRepositoryPaging.collect(pageSize = 0) { emptyList() } }
        }
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { GitHubRepositoryPaging.collect(maxPages = 51) { emptyList() } }
        }
    }

    @Test
    fun propagatesFetchFailures() {
        assertThrows(IOException::class.java) {
            runBlocking {
                GitHubRepositoryPaging.collect(pageSize = 3) {
                    throw IOException("GitHub HTTP 429")
                }
            }
        }
    }
}
