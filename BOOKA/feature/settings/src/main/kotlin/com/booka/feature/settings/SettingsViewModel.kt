package com.booka.feature.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * PART 1 전용 설정 화면 상태. 실제 영속화(:core:datastore, Android Keystore)는 PART 2~3에서 연결되며,
 * 여기서는 화면 흐름·토글·다이얼로그 상호작용만 검증한다(지시서 7.1).
 */
@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun setShelfViewMode(mode: ShelfViewMode) { _uiState.value = _uiState.value.copy(shelfViewMode = mode) }
    fun setDarkModeFollowSystem(value: Boolean) { _uiState.value = _uiState.value.copy(darkModeFollowSystem = value) }
    fun setForceDarkMode(value: Boolean) { _uiState.value = _uiState.value.copy(forceDarkMode = value) }

    fun setNaverEnabled(value: Boolean) {
        if (value) {
            requestNotYetImplemented("Naver API 키 암호화 저장", "PART 2")
            return
        }
        _uiState.value = _uiState.value.copy(
            providerToggles = _uiState.value.providerToggles.copy(naverEnabled = false),
        )
    }
    fun setGoogleBooksEnabled(value: Boolean) {
        _uiState.value = _uiState.value.copy(
            providerToggles = _uiState.value.providerToggles.copy(googleBooksEnabled = value),
        )
    }
    fun setOpenLibraryEnabled(value: Boolean) {
        _uiState.value = _uiState.value.copy(
            providerToggles = _uiState.value.providerToggles.copy(openLibraryEnabled = value),
        )
    }

    fun setReaderFontScale(value: Float) { _uiState.value = _uiState.value.copy(readerFontScale = value) }
    fun setReaderLineSpacing(value: Float) { _uiState.value = _uiState.value.copy(readerLineSpacing = value) }
    fun setReaderTheme(theme: ReaderTheme) { _uiState.value = _uiState.value.copy(readerTheme = theme) }

    fun requestClearCache() = requestNotYetImplemented("캐시 삭제", "PART 2")
    fun requestBackup() = requestNotYetImplemented("데이터 백업", "PART 3")
    fun requestRestore() = requestNotYetImplemented("데이터 복원", "PART 3")
    fun requestExportErrorLog() = requestNotYetImplemented("오류 로그 내보내기", "PART 3")

    fun requestReset() { _uiState.value = _uiState.value.copy(isResetConfirmVisible = true) }
    fun confirmResetFirstStep() {
        _uiState.value = _uiState.value.copy(isResetConfirmVisible = false, isResetSecondConfirmVisible = true)
    }
    fun dismissResetFirstStep() { _uiState.value = _uiState.value.copy(isResetConfirmVisible = false) }
    fun confirmResetFinal() {
        // 실제 초기화(Room/DataStore)는 PART 2~3에서 연결된다. PART 1은 이중 확인 흐름만 검증한다.
        _uiState.value = _uiState.value.copy(isResetSecondConfirmVisible = false)
    }
    fun dismissResetSecondStep() { _uiState.value = _uiState.value.copy(isResetSecondConfirmVisible = false) }

    private fun requestNotYetImplemented(feature: String, part: String) {
        _uiState.value = _uiState.value.copy(notYetImplementedFeature = feature to part)
    }
    fun dismissNotYetImplemented() { _uiState.value = _uiState.value.copy(notYetImplementedFeature = null) }
}
