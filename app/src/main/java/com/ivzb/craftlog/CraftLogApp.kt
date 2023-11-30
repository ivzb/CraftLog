package com.ivzb.craftlog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ivzb.craftlog.analytics.AnalyticsEvents
import com.ivzb.craftlog.analytics.AnalyticsHelper
import com.ivzb.craftlog.feature.addexpense.navigation.AddExpenseDestination
import com.ivzb.craftlog.feature.expenses.ExpensesDestination
import com.ivzb.craftlog.feature.home.HomeDestination
import com.ivzb.craftlog.navigation.CraftLogNavHost
import com.ivzb.craftlog.navigation.CraftLogTopLevelNavigation
import com.ivzb.craftlog.navigation.TOP_LEVEL_DESTINATIONS
import com.ivzb.craftlog.navigation.TopLevelDestination
import com.ivzb.craftlog.ui.theme.CraftLogTheme
import com.ivzb.craftlog.util.SnackbarUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CraftLogApp() {
    CraftLogTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            val navController = rememberNavController()
            val topLevelNavigation = remember(navController) {
                CraftLogTopLevelNavigation(navController)
            }

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val bottomBarVisibility = rememberSaveable { (mutableStateOf(true)) }
            val fabVisibility = rememberSaveable { (mutableStateOf(true)) }

            val context = LocalContext.current
            val analyticsHelper = AnalyticsHelper.getInstance(context)

            Scaffold(
                modifier = Modifier.padding(16.dp, 0.dp),
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                floatingActionButton = {
                    AnimatedVisibility(
                        visible = fabVisibility.value,
                        enter = slideInVertically(
                            initialOffsetY = { 600 }, // small slide 300px
                            animationSpec = tween(
                                durationMillis = 1000,
                                easing = FastOutSlowInEasing
                            )
                        ),
                        exit = slideOutVertically(
                            targetOffsetY = { 600 },
                            animationSpec = tween(
                                durationMillis = 1000,
                                easing = FastOutSlowInEasing
                            )
                        ),
                        content = {
                            CraftLogFAB(navController, analyticsHelper)
                        }
                    )
                },
                bottomBar = {
                    Box {
                        SnackbarUtil.SnackbarWithoutScaffold(
                            SnackbarUtil.getSnackbarMessage().component1(),
                            SnackbarUtil.isSnackbarVisible().component1()
                        ) {
                            SnackbarUtil.hideSnackbar()
                        }

                        AnimatedVisibility(
                            visible = bottomBarVisibility.value,
                            enter = slideInVertically(
                                initialOffsetY = { 300 },
                                animationSpec = tween(
                                    durationMillis = 1000,
                                    easing = FastOutSlowInEasing
                                )
                            ),
                            exit = slideOutVertically(
                                targetOffsetY = { 300 },
                                animationSpec = tween(
                                    durationMillis = 1000,
                                    easing = FastOutSlowInEasing
                                )
                            ),
                            content = {
                                CraftLogBottomBar(
                                    onNavigateToTopLevelDestination = topLevelNavigation::navigateTo,
                                    currentDestination = currentDestination,
                                    analyticsHelper = analyticsHelper
                                )
                            }
                        )
                    }
                }
            ) { padding ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal
                            )
                        )
                ) {
                    CraftLogNavHost(
                        bottomBarVisibility = bottomBarVisibility,
                        fabVisibility = fabVisibility,
                        navController = navController,
                        modifier = Modifier
                            .padding(padding)
                            .consumeWindowInsets(padding)
                            .zIndex(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun CraftLogBottomBar(
    onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    analyticsHelper: AnalyticsHelper
) {
    // Wrap the navigation bar in a surface so the color behind the system
    // navigation is equal to the container color of the navigation bar.
    Surface(color = MaterialTheme.colorScheme.surface) {
        NavigationBar(
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            ),
            tonalElevation = 0.dp
        ) {

            TOP_LEVEL_DESTINATIONS.forEach { destination ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == destination.route } == true
                NavigationBarItem(
                    selected = selected,
                    onClick =
                    {
                        trackTabClicked(analyticsHelper, destination.route)
                        onNavigateToTopLevelDestination(destination)
                    },
                    icon = {
                        Icon(
                            if (selected) {
                                destination.selectedIcon
                            } else {
                                destination.unselectedIcon
                            },
                            contentDescription = null
                        )
                    },
                    label = { Text(stringResource(destination.iconTextId)) }
                )
            }
        }
    }
}

private fun trackTabClicked(analyticsHelper: AnalyticsHelper, route: String) {
    if (route == HomeDestination.route) {
        analyticsHelper.logEvent(AnalyticsEvents.HOME_TAB_CLICKED)
    }

    if (route == ExpensesDestination.route) {
        analyticsHelper.logEvent(AnalyticsEvents.EXPENSES_TAB_CLICKED)
    }
}

@Composable
fun CraftLogFAB(navController: NavController, analyticsHelper: AnalyticsHelper) {
    ExtendedFloatingActionButton(
        text = { Text(text = stringResource(id = R.string.add_expense)) },
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add)
            )
        },
        onClick = {
            analyticsHelper.logEvent(AnalyticsEvents.ADD_EXPENSE_CLICKED_FAB)
            navController.navigate(AddExpenseDestination.route)
        },
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
        containerColor = MaterialTheme.colorScheme.tertiary
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CraftLogTheme {
        CraftLogApp()
    }
}