package com.booka.feature.library

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.booka.core.navigation.BookaDestination

/** :feature:library의 navigation entry. 다른 feature 구현체를 직접 참조하지 않는다(지시서 4.1). */
fun NavGraphBuilder.libraryGraph(navController: NavHostController) {
    composable<BookaDestination.Library> {
        LibraryScreen(
            onSearchClick = { navController.navigate(BookaDestination.LibrarySearch()) },
            onWorkClick = { workId -> navController.navigate(BookaDestination.WorkDetail(workId)) },
        )
    }
    composable<BookaDestination.LibrarySearch> { backStackEntry ->
        val route = backStackEntry.toRoute<BookaDestination.LibrarySearch>()
        LibrarySearchScreen(
            initialQuery = route.initialQuery,
            onBack = { navController.popBackStack() },
            onWorkClick = { workId -> navController.navigate(BookaDestination.WorkDetail(workId)) },
        )
    }
    composable<BookaDestination.WorkDetail> {
        WorkDetailScreen(
            onBack = { navController.popBackStack() },
            onReadNovel = { workId -> navController.navigate(BookaDestination.NovelReader(workId)) },
            onReadComic = { workId -> navController.navigate(BookaDestination.ComicReader(workId)) },
        )
    }
}
