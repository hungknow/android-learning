package com.example.learning.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch


@Composable
fun DL1(
    windowSizeClass: WindowSizeClass
) {
    val navigationType: NavigationType
    val contentType: ContentType
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM
            contentType = ContentType.ONE_PANEL
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.RAIL
            contentType = ContentType.ONE_PANEL
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = NavigationType.PERMANENT
            contentType = ContentType.DOUBLE_PANEL
        }

        else -> {
            navigationType = NavigationType.RAIL
            contentType = ContentType.DOUBLE_PANEL
        }
    }

    DL1Wrapper(
        navigationType = navigationType,
        contentType = contentType,
    )
}

@Composable
fun DL1Wrapper(
    navigationType: NavigationType,
    contentType: ContentType,
) {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        DL1NavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: DL1Route.INBOX

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    when (navigationType) {
        NavigationType.PERMANENT -> {
            PermanentNavigationDrawer(drawerContent = {
                ModalDrawerSheet {
                    DL1PermanentNavigationDrawerContent(
                        selectedDestination = selectedDestination,
                        navigationContentPosition = NavigationContentPosition.CENTER,
                        navigateToTopLevelDestination = navigationActions::navigateTo,
                    )
                }
            }) {
                DL1AppContent(
                    navController = navController,
                )
            }
        }

        NavigationType.RAIL -> {
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet {
                        DL1ModalNavigationDrawerContent(
                            selectedDestination = selectedDestination,
                            navigationContentPosition = NavigationContentPosition.CENTER,
                            onDrawerClicked = {
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        )
                    }
                },
                drawerState = drawerState
            ) {
                Row {
                    DL1NavigationRail(
                        selectedDestination = selectedDestination,
                        onDrawerClicked = {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                        navigateToTopLevelDestination = navigationActions::navigateTo,
                    )
                    DL1AppContent(
                        navController = navController
                    )
                }
            }
        }

        else -> {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (content, bottomNavigationBar) = createRefs()
                DL1AppContent(
                    navController = navController,
                    modifier = Modifier.constrainAs(content) {
                        top.linkTo(parent.top)
                    },
                )

                DL1BottomNavigationBar(
                    modifier = Modifier.constrainAs(bottomNavigationBar) {
                        bottom.linkTo(parent.bottom)
                    },
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigationActions::navigateTo,
                )
            }
        }
    }
}

enum class LayoutType {
    HEADER, CONTENT
}

fun navigationMeasurePolicy(navigationContentPosition: NavigationContentPosition): MeasurePolicy {
    return MeasurePolicy { measurables, constraints ->
        lateinit var headerMeasurable: Measurable
        lateinit var contentMeasurable: Measurable

        measurables.forEach {
            when (it.layoutId) {
                LayoutType.HEADER -> headerMeasurable = it
                LayoutType.CONTENT -> contentMeasurable = it
                else -> error("Unknown layoutId encountered!")
            }
        }

        val headerPlaceable = headerMeasurable.measure(constraints)
        val contentPlaceable = contentMeasurable.measure(
            constraints.offset(vertical = -headerPlaceable.height)
        )

        layout(constraints.maxWidth, constraints.maxHeight) {
            headerPlaceable.placeRelative(0, 0)
            val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height

            val contentPlaceableY = when (navigationContentPosition) {
                // Figure out the place we want to place the content, with respect to the
                // parent (ignoring the header for now)
                NavigationContentPosition.TOP -> 0
                NavigationContentPosition.CENTER -> nonContentVerticalSpace / 2
            }.coerceAtLeast(headerPlaceable.height)

            contentPlaceable.placeRelative(0, contentPlaceableY)
        }
    }
}

data class DL1TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: String
)

object DL1Route {
    const val INBOX = "Inbox"
    const val ARTICLES = "Articles"
    const val DM = "DirectMessages"
    const val GROUPS = "Groups"
}

val TOP_LEVEL_DESTINATIONS = listOf(
    DL1TopLevelDestination(
        route = DL1Route.INBOX,
        selectedIcon = Icons.Default.Inbox,
        unselectedIcon = Icons.Default.Inbox,
        iconText = "Inbox"
    ),
    DL1TopLevelDestination(
        route = DL1Route.ARTICLES,
        selectedIcon = Icons.Default.Article,
        unselectedIcon = Icons.Default.Article,
        iconText = "Article"
    ),
    DL1TopLevelDestination(
        route = DL1Route.DM,
        selectedIcon = Icons.Outlined.ChatBubbleOutline,
        unselectedIcon = Icons.Outlined.ChatBubbleOutline,
        iconText = "DM"
    ),
    DL1TopLevelDestination(
        route = DL1Route.GROUPS,
        selectedIcon = Icons.Default.People,
        unselectedIcon = Icons.Default.People,
        iconText = "Article"
    )
)

@Composable
fun DL1PermanentNavigationDrawerContent(
    selectedDestination: String,
    navigationContentPosition: NavigationContentPosition,
    navigateToTopLevelDestination: (DL1TopLevelDestination) -> Unit,
) {
    PermanentDrawerSheet(
        modifier = Modifier.sizeIn(minWidth = 200.dp, maxWidth = 300.dp),
        drawerContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
    ) {
        Layout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(16.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = "app".uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 40.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "COMPOSE",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Column(
                    modifier = Modifier.layoutId(LayoutType.CONTENT)
                ) {
                    TOP_LEVEL_DESTINATIONS.forEach { destination ->
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = destination.iconText,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.selectedIcon,
                                    contentDescription = destination.iconText,
                                )
                            },
                            selected = selectedDestination == destination.route,
                            onClick = { navigateToTopLevelDestination(destination) },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent
                            )
                        )
                    }
                }
            },
            measurePolicy = navigationMeasurePolicy(navigationContentPosition)
        )
    }
}

@Composable
fun DL1ModalNavigationDrawerContent(
    selectedDestination: String,
    navigationContentPosition: NavigationContentPosition,
    onDrawerClicked: () -> Unit = {}
) {
    ModalDrawerSheet {
        Layout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(16.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "app".uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(onClick = onDrawerClicked) {
                            Icon(
                                imageVector = Icons.Default.MenuOpen,
                                contentDescription = null
                            )
                        }
                    }

                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 40.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "COMPOSE",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Column(
                    modifier = Modifier.layoutId(LayoutType.CONTENT)
                ) {
                    TOP_LEVEL_DESTINATIONS.forEach { destination ->
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = destination.iconText,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.selectedIcon,
                                    contentDescription = destination.iconText,
                                )
                            },
                            selected = selectedDestination == destination.route,
                            onClick = { /*TODO*/ },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent
                            )
                        )
                    }
                }
            },
            measurePolicy = navigationMeasurePolicy(navigationContentPosition)

        )
    }
}

@Composable
fun DL1NavigationRail(
    selectedDestination: String,
    navigateToTopLevelDestination: (DL1TopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {},
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Column(
            modifier = Modifier.layoutId(LayoutType.HEADER),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            NavigationRailItem(
                selected = false,
                onClick = onDrawerClicked,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null
                    )
                }
            )
            FloatingActionButton(
                onClick = {},
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                )
            }
            Spacer(Modifier.height(8.dp)) // NavigationRailHeaderPadding
            Spacer(Modifier.height(4.dp)) // NavigationRailVerticalPadding
        }

        Column(
            modifier = Modifier.layoutId(LayoutType.CONTENT),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TOP_LEVEL_DESTINATIONS.forEach { destination ->
                NavigationRailItem(
//                    label = {
//                        Text(
//                            text = destination.iconText,
//                            modifier = Modifier.padding(horizontal = 16.dp)
//                        )
//                    },
                    icon = {
                        Icon(
                            imageVector = destination.selectedIcon,
                            contentDescription = destination.iconText,
                        )
                    },
                    selected = selectedDestination == destination.route,
                    onClick = { navigateToTopLevelDestination(destination) },
                )
            }
        }
    }
}

@Composable
fun DL1BottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedDestination: String,
    navigateToTopLevelDestination: (DL1TopLevelDestination) -> Unit
) {
    NavigationBar(modifier = modifier.fillMaxWidth()) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            NavigationBarItem(
                selected = selectedDestination == destination.route,
                onClick = { navigateToTopLevelDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null
                    )
                })
        }
    }
}

@Composable
fun DL1AppContent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
//        Text("DashboardAppContent")
        DL1AppHost(
            navController = navController,
        )
    }
}

@Composable
fun DL1AppHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = DL1Route.DM,
        modifier = modifier,
    ) {
        composable(DL1Route.INBOX) {
            Text("Inbox")
        }
        composable(DL1Route.DM) {
            Text("DM")
        }
        composable(DL1Route.ARTICLES) {
            Text("ARTICLES")
        }
        composable(DL1Route.GROUPS) {
            Text("GROUPS")
        }
    }
}

class DL1NavigationActions(private val navController: NavHostController) {
    fun navigateTo(destination: DL1TopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun InMobile() {
    DL1(
        windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(400.dp, 900.dp))
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 780, heightDp = 1024)
@Composable
fun InTablet() {
    DL1(
        windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(780.dp, 1024.dp))
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 1024, heightDp = 780)
@Composable
fun InDesktop() {
    DL1(
        windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(1024.dp, 780.dp))
    )
}