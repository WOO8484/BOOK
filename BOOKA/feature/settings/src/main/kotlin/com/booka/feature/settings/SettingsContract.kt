package com.booka.feature.settings

enum class ShelfViewMode { GRID, LIST }
enum class ReaderTheme(val label: String) { LIGHT("밝게"), DARK("어둡게"), SEPIA("세피아") }

data class ProviderToggleState(
    val naverEnabled: Boolean = false,
    val googleBooksEnabled: Boolean = true,
    val openLibraryEnabled: Boolean = true,
)

data class SettingsUiState(
    val shelfViewMode: ShelfViewMode = ShelfViewMode.GRID,
    val darkModeFollowSystem: Boolean = true,
    val forceDarkMode: Boolean = false,
    val providerToggles: ProviderToggleState = ProviderToggleState(),
    val readerFontScale: Float = 1.0f,
    val readerLineSpacing: Float = 1.0f,
    val readerTheme: ReaderTheme = ReaderTheme.LIGHT,
    val cacheSizeLabel: String = "128 MB (샘플 수치)",
    val isResetConfirmVisible: Boolean = false,
    val isResetSecondConfirmVisible: Boolean = false,
    val notYetImplementedFeature: Pair<String, String>? = null,
)
