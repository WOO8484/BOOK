package com.booka.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.booka.core.navigation.BookaDestination

private data class BottomTab(
    val destination: BookaDestination,
    val label: String,
    val icon: ImageVector,
)

private val bottomTabs = listOf(
    BottomTab(BookaDestination.Library, "서재", Icons.AutoMirrored.Filled.MenuBook),
    BottomTab(BookaDestination.Import, "가져오기", Icons.Filled.FileDownload),
    BottomTab(BookaDestination.Settings, "설정", Icons.Filled.Settings),
)

/**
 * 하단 메뉴(서재/가져오기/설정). 지시서 6.1 잔상·중복 이동 방지를 위해
 * launchSingleTop + restoreState + popUpTo(startDestination, saveState=true) 표준 패턴을 사용한다.
 * 같은 탭을 반복해서 눌러도 동일 destination이 중복 생성되지 않는다.
 */
@Composable
fun BookaBottomBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = backStackEntry?.destination

    NavigationBar {
        bottomTabs.forEach { tab ->
            val selected = when (tab.destination) {
                BookaDestination.Library -> currentDestination.isRoute<BookaDestination.Library>()
                BookaDestination.Import -> currentDestination.isRoute<BookaDestination.Import>()
                BookaDestination.Settings -> currentDestination.isRoute<BookaDestination.Settings>()
                else -> false
            }
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(tab.destination) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) },
            )
        }
    }
}

private inline fun <reified T : Any> NavDestination?.isRoute(): Boolean = this?.hasRoute<T>() == true
