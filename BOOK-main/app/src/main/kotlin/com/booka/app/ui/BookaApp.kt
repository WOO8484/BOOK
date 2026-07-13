package com.booka.app.ui

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.booka.app.navigation.BookaBottomBar
import com.booka.app.navigation.BookaNavHost
import com.booka.core.navigation.BookaDestination

/** 앱 최상위 화면 조립. 소설·만화 뷰어에서는 하단 메뉴를 숨긴다(지시서 3.1). */
@Composable
fun BookaApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val hideBottomBar = currentDestination?.hasRoute<BookaDestination.NovelReader>() == true ||
        currentDestination?.hasRoute<BookaDestination.ComicReader>() == true

    Scaffold(
        bottomBar = { if (!hideBottomBar) BookaBottomBar(navController) },
    ) { innerPadding ->
        BookaNavHost(
            navController = navController,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
        )
    }
}
