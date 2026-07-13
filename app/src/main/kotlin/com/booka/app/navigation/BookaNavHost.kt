package com.booka.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.booka.core.navigation.BookaDestination
import com.booka.feature.importer.importGraph
import com.booka.feature.library.libraryGraph
import com.booka.feature.metadata.metadataGraph
import com.booka.feature.reader.comic.comicReaderGraph
import com.booka.feature.reader.novel.novelReaderGraph
import com.booka.feature.settings.settingsGraph

/** 최상위 NavHost 조립. :app은 세부 데이터 구현을 직접 포함하지 않고 feature graph만 연결한다(지시서 4.1). */
@Composable
fun BookaNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BookaDestination.Library,
        modifier = modifier,
    ) {
        libraryGraph(navController)
        importGraph(navController)
        metadataGraph(navController)
        novelReaderGraph(navController)
        comicReaderGraph(navController)
        settingsGraph(navController)
    }
}
