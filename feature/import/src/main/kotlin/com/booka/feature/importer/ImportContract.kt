package com.booka.feature.importer

data class SampleImportFile(val name: String, val sizeLabel: String, val isSelected: Boolean = false)

data class ImportUiState(
    val availableFiles: List<SampleImportFile> = listOf(
        SampleImportFile("달빛_아래_도서관_최종본.txt", "1.2 MB"),
        SampleImportFile("철갑기병_크로니클_01-08.cbz", "184 MB"),
        SampleImportFile("붉은_항성계.zip", "980 KB"),
        SampleImportFile("마을버스_정류장 (이미지 폴더)", "폴더 · 42개 이미지"),
    ),
    val isStarting: Boolean = false,
    val errorMessage: String? = null,
)

sealed interface ImportUiAction {
    data class ToggleFile(val name: String) : ImportUiAction
    data object StartImport : ImportUiAction
    data object DismissError : ImportUiAction
}
