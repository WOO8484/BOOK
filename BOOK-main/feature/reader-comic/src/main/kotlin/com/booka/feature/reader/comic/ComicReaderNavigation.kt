package com.booka.feature.reader.comic

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.booka.core.navigation.BookaDestination

fun NavGraphBuilder.comicReaderGraph(navController: NavHostController) {
    composable<BookaDestination.ComicReader> {
        ComicReaderScreen(onBack = { navController.popBackStack() })
    }
}
