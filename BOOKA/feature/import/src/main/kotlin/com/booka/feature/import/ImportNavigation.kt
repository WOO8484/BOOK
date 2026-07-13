package com.booka.feature.import

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.booka.core.navigation.BookaDestination

fun NavGraphBuilder.importGraph(navController: NavHostController) {
    composable<BookaDestination.Import> {
        ImportScreen(
            onNavigateToProgress = { sessionId ->
                navController.navigate(BookaDestination.ImportProgress(sessionId))
            },
        )
    }
    composable<BookaDestination.ImportProgress> { backStackEntry ->
        val route = backStackEntry.toRoute<BookaDestination.ImportProgress>()
        ImportProgressScreen(
            onDone = {
                navController.navigate(BookaDestination.Library) {
                    popUpTo(BookaDestination.Library) { inclusive = true }
                }
            },
            onOpenRenamePreview = { navController.navigate(BookaDestination.RenamePreview(route.sessionId)) },
        )
    }
    composable<BookaDestination.RenamePreview> {
        RenamePreviewScreen(onConfirm = { navController.popBackStack() })
    }
}
