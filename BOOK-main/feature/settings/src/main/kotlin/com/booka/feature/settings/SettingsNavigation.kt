package com.booka.feature.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.booka.core.navigation.BookaDestination

fun NavGraphBuilder.settingsGraph(navController: NavHostController) {
    composable<BookaDestination.Settings> {
        SettingsMainScreen(onNavigate = { destination -> navController.navigate(destination) })
    }
    composable<BookaDestination.SettingsShelfDisplay> {
        ShelfDisplaySettingsScreen(onBack = { navController.popBackStack() })
    }
    composable<BookaDestination.SettingsMetadataProviders> {
        MetadataProviderSettingsScreen(onBack = { navController.popBackStack() })
    }
    composable<BookaDestination.SettingsReaderDefaults> {
        ReaderDefaultsSettingsScreen(onBack = { navController.popBackStack() })
    }
    composable<BookaDestination.SettingsStorage> {
        StorageSettingsScreen(onBack = { navController.popBackStack() })
    }
    composable<BookaDestination.SettingsAbout> {
        AboutScreen(onBack = { navController.popBackStack() })
    }
}
