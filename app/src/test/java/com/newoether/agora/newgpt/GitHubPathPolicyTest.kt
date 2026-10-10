package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class GitHubPathPolicyTest {
    @Test
    fun repositoryParts_acceptsOwnerRepository() {
        assertEquals("mojealterego" to "THOR-AI", GitHubPathPolicy.repositoryParts(" mojealterego/THOR-AI "))
    }

    @Test
    fun repositoryParts_rejectsSlashAndQueryInjection() {
        listOf("owner/repo/extra", "owner/repo?ref=evil", "owner/", "/repo", "a b/repo", "owner/repo#frag")
            .forEach { invalid ->
                assertThrows(IllegalArgumentException::class.java) {
                    GitHubPathPolicy.repositoryParts(invalid)
                }
            }
    }

    @Test
    fun filePathSegments_acceptsRelativePathsWithSpacesAndUnicode() {
        assertEquals(
            listOf("docs", "Opis THOR", "Źródła.md"),
            GitHubPathPolicy.filePathSegments("docs/Opis THOR/Źródła.md"),
        )
    }

    @Test
    fun filePathSegments_rejectsTraversalAndEmptyComponents() {
        listOf("", "/secret", "a/", "a//b", "a/./b", "a/../b", "../secret", "a\\b", "a\n/b")
            .forEach { invalid ->
                assertThrows(IllegalArgumentException::class.java) {
                    GitHubPathPolicy.filePathSegments(invalid)
                }
            }
    }

    @Test
    fun filePathSegments_rejectsOversizedPath() {
        assertThrows(IllegalArgumentException::class.java) {
            GitHubPathPolicy.filePathSegments("a".repeat(1025))
        }
    }

    @Test
    fun branchRef_acceptsScopedRefsAndMaster() {
        listOf("master", "main", "feature/security-fix", "release/2.1.0")
            .forEach { assertEquals(it, GitHubPathPolicy.branchRef(it)) }
    }

    @Test
    fun branchRef_rejectsInvalidGitReferencesAndQueryInjection() {
        listOf("", "feature//x", "../main", "main..fix", "main.lock", "/main", "main/", "main?ref=x", "main#frag", "main@{1}", "a\\b", "a b")
            .forEach { invalid ->
                assertThrows(IllegalArgumentException::class.java) {
                    GitHubPathPolicy.branchRef(invalid)
                }
            }
    }
}
