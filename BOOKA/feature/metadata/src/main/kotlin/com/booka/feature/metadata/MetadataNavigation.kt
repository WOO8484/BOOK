package com.booka.feature.metadata

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.booka.core.navigation.BookaDestination

fun NavGraphBuilder.metadataGraph(navController: NavHostController) {
    composable<BookaDestination.MetadataAnalysis> { backStackEntry ->
        val route = backStackEntry.toRoute<BookaDestination.MetadataAnalysis>()
        MetadataAnalysisScreen(
            onAnalysisDone = { workDraftId ->
                navController.navigate(BookaDestination.MetadataCandidateSelect(route.sessionId, workDraftId)) {
                    popUpTo(BookaDestination.MetadataAnalysis(route.sessionId)) { inclusive = true }
                }
            },
        )
    }
    composable<BookaDestination.MetadataCandidateSelect> { backStackEntry ->
        val route = backStackEntry.toRoute<BookaDestination.MetadataCandidateSelect>()
        MetadataCandidateSelectScreen(
            onCandidateApplied = { navController.popBackStack(BookaDestination.Import, inclusive = false) },
            onManualEdit = {
                navController.navigate(BookaDestination.MetadataManualEdit(route.sessionId, route.workDraftId))
            },
        )
    }
    composable<BookaDestination.MetadataManualEdit> {
        MetadataManualEditScreen(
            onSaved = { navController.popBackStack(BookaDestination.Import, inclusive = false) },
        )
    }
}
