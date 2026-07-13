package com.booka.feature.reader.novel

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.booka.core.navigation.BookaDestination

fun NavGraphBuilder.novelReaderGraph(navController: NavHostController) {
    composable<BookaDestination.NovelReader> {
        NovelReaderScreen(onBack = { navController.popBackStack() })
    }
}
