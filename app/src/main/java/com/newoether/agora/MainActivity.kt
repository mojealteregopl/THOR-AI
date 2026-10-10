package com.newoether.agora
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.AccessibilityManager
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.newoether.agora.ui.settings.RatingForm
import com.newoether.agora.newgpt.NewGptStudioScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.newoether.agora.data.SettingsManager
import com.newoether.agora.service.AgoraForegroundService
import com.newoether.agora.service.AppForegroundTracker
import com.newoether.agora.ui.chat.ChatApp
import com.newoether.agora.ui.chat.FullScreenMediaPreviewDialog
import com.newoether.agora.ui.chat.MediaPreviewTarget
import com.newoether.agora.ui.chat.message.ChatMarkdownCodeBlock
import com.newoether.agora.ui.onboarding.WelcomeScreen
import com.newoether.agora.ui.motion.LocalAgoraMotionPolicy
import com.newoether.agora.ui.motion.ProvideAgoraMotionPolicy
import com.newoether.agora.ui.settings.SettingsScreen
import com.newoether.agora.ui.tasks.TaskEditorSessionViewModel
import com.newoether.agora.ui.tasks.TaskHistoryPreviewPhase
import com.newoether.agora.ui.theme.AgoraTheme
import com.newoether.agora.util.CrashReporter
import com.newoether.agora.viewmodel.ChatViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first


class MainActivity : ComponentActivity() {

    private val notificationConversationId = kotlinx.coroutines.flow.MutableStateFlow<String?>(null)

    companion object {
        const val EXTRA_CONVERSATION_ID = "com.newoether.agora.extra.CONVERSATION_ID"
    }

    override fun attachBaseContext(newBase: Context) {
        val langCode = kotlinx.coroutines.runBlocking {
            SettingsManager(newBase).appLanguage.first()
        }
        val locale = when (langCode) {
            "pl" -> java.util.Locale("pl", "PL")
            "zh" -> java.util.Locale("zh", "CN")
            "en" -> java.util.Locale("en")
            "es" -> java.util.Locale("es")
            "fr" -> java.util.Locale("fr")
            "de" -> java.util.Locale("de")
            "ru" -> java.util.Locale("ru")
            "pt-BR" -> java.util.Locale("pt", "BR")
            "ja" -> java.util.Locale("ja")
            "ko" -> java.util.Locale("ko")
            "ar" -> java.util.Locale("ar")
            "vi" -> java.util.Locale("vi")
            "zh-Hant" -> java.util.Locale.forLanguageTag("zh-Hant")
            else -> null
        }
        if (locale != null) {
            java.util.Locale.setDefault(locale)
            val config = android.content.res.Configuration(newBase.resources.configuration)
            config.setLocale(locale)
            super.attachBaseContext(newBase.createConfigurationContext(config))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var startupReady = false
        splashScreen.setKeepOnScreenCondition { !startupReady }
        super.onCreate(savedInstanceState)
        handleNavigationIntent(intent)

        com.newoether.agora.util.DebugLog.init(this)

        val settingsManager = SettingsManager(applicationContext)
        val agoraApplication = application as AgoraApplication
        lifecycleScope.launch {
            val databaseStartupState = agoraApplication.awaitDatabaseStartup()
            val needsErrorDialog = databaseStartupState is DatabaseStartupState.Blocked
            withContext(Dispatchers.IO) {
                runCatching {
                    settingsManager.initializeFirstInstallDefaults(
                        locale = java.util.Locale.getDefault()
                    )
                }.onFailure { error ->
                    com.newoether.agora.util.DebugLog.e(
                        "MainActivity",
                        "First-install settings initialization failed",
                        error,
                    )
                }
            }

            enableEdgeToEdge()
            // Remove navigation bar scrim so it blends with app content
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
            setContent {
            val themeMode by settingsManager.themeMode.collectAsState(initial = "FOLLOW_DEVICE")
            val amoledEnabled by settingsManager.amoledEnabled.collectAsState(initial = false)
            val colorSchemeName by settingsManager.colorScheme.collectAsState(initial = com.newoether.agora.data.DEFAULT_COLOR_SCHEME)
            val schemeStyleName by settingsManager.schemeStyle.collectAsState(initial = com.newoether.agora.data.DEFAULT_SCHEME_STYLE)
            val dynamicColor by settingsManager.dynamicColor.collectAsState(initial = com.newoether.agora.data.DEFAULT_DYNAMIC_COLOR)
            val fontPreference by settingsManager.fontPreference.collectAsState(initial = "app_default")
            val customFontPath by settingsManager.customFontPath.collectAsState(initial = "")
            val appReduceMotion by settingsManager.reduceMotion.collectAsState(initial = false)

            val themeModeEnum = try { com.newoether.agora.ui.theme.ThemeMode.valueOf(themeMode) } catch (_: Exception) { com.newoether.agora.ui.theme.ThemeMode.FOLLOW_DEVICE }
            val colorSchemePreset = try { com.newoether.agora.ui.theme.ColorSchemePreset.valueOf(colorSchemeName) } catch (_: Exception) { com.newoether.agora.ui.theme.ColorSchemePreset.FOREST }
            val schemeStyle = try { com.newoether.agora.ui.theme.SchemeStyle.valueOf(schemeStyleName) } catch (_: Exception) { com.newoether.agora.ui.theme.SchemeStyle.TONAL_SPOT }

            val systemDark = isSystemInDarkTheme()
            val isDark = when (themeModeEnum) {
                com.newoether.agora.ui.theme.ThemeMode.LIGHT -> false
                com.newoether.agora.ui.theme.ThemeMode.DARK -> true
                com.newoether.agora.ui.theme.ThemeMode.FOLLOW_DEVICE -> systemDark
            }

            SideEffect {
                val window = this@MainActivity.window
                val insetsController = androidx.core.view.WindowInsetsControllerCompat(window, window.decorView)
                insetsController.isAppearanceLightStatusBars = !isDark
                insetsController.isAppearanceLightNavigationBars = !isDark
            }

            AgoraTheme(
                themeMode = themeModeEnum,
                amoledEnabled = amoledEnabled,
                colorSchemePreset = colorSchemePreset,
                schemeStyle = schemeStyle,
                dynamicColor = dynamicColor,
                fontPreference = fontPreference,
                customFontPath = customFontPath
            ) {
                ProvideAgoraMotionPolicy(appReduceMotion = appReduceMotion) {
                val activity = LocalActivity.current

                if (needsErrorDialog) {
                    val databaseScope = rememberCoroutineScope()
                    var clearingDatabase by remember { mutableStateOf(false) }
                    AlertDialog(
                        onDismissRequest = { activity?.finish() },
                        title = { Text(stringResource(R.string.database_incompatible), fontWeight = FontWeight.Bold) },
                        text = { Text(stringResource(R.string.database_incompatible_desc)) },
                        dismissButton = {
                            TextButton(onClick = { activity?.finish() }) { Text(stringResource(R.string.quit)) }
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    if (!clearingDatabase) {
                                        clearingDatabase = true
                                        databaseScope.launch {
                                            val cleared = agoraApplication.clearIncompatibleDatabase()
                                            if (cleared) {
                                                activity?.recreate()
                                            } else {
                                                clearingDatabase = false
                                            }
                                        }
                                    }
                                },
                                enabled = !clearingDatabase,
                            ) { Text(stringResource(R.string.clear_database)) }
                        }
                    )
                } else {
                    var showOnboarding by remember { mutableStateOf<Boolean?>(null) }
                    val onboardingScope = rememberCoroutineScope()

                    LaunchedEffect(Unit) {
                        showOnboarding = !settingsManager.onboardingCompleted.first()
                    }

                    // Create ViewModel via the process-scoped DI container (owned by AgoraApplication),
                    // so the same shared singletons back both the UI and background task execution.
                    val container = agoraApplication.requireContainer()
                    val factory = remember { container.chatViewModelFactory() }
                    val viewModel: ChatViewModel = viewModel(factory = factory)

                    when (showOnboarding) {
                        null -> { /* loading — splash screen covers this */ }
                        true -> {
                            WelcomeScreen(
                                onComplete = {
                                    onboardingScope.launch {
                                        settingsManager.saveOnboardingCompleted(true)
                                    }
                                    showOnboarding = false
                                },
                                isDarkTheme = isDark,
                                viewModel = viewModel
                            )
                        }
                        false -> {
                            MainNavigation(
                                viewModel = viewModel,
                                settingsManager = settingsManager,
                                notificationConversationId = notificationConversationId,
                                onNotificationConversationConsumed = { expectedId ->
                                    consumeNotificationTarget(notificationConversationId, expectedId)
                                },
                            )
                        }
                    }
                }
            }
            }
            }
            startupReady = true
        }
    }

    override fun onResume() {
        super.onResume()
        AppForegroundTracker.setInForeground(true)
    }

    override fun onPause() {
        super.onPause()
        AppForegroundTracker.setInForeground(false)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleNavigationIntent(intent)
    }

    private fun handleNavigationIntent(intent: Intent?) {
        notificationConversationId.value = intent?.getStringExtra(EXTRA_CONVERSATION_ID)
            ?.takeIf { it.isNotBlank() }
            ?: intent?.data?.takeIf { uri ->
                uri.scheme == "agora" && uri.host == "conversation"
            }?.lastPathSegment?.takeIf { it.isNotBlank() }
    }
}


internal fun consumeNotificationTarget(
    target: kotlinx.coroutines.flow.MutableStateFlow<String?>,
    expectedId: String,
): Boolean = target.compareAndSet(expectedId, null)

