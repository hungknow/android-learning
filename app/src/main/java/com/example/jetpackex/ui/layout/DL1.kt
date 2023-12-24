package com.example.jetpackex.ui.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
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
        contentType = contentType
    )
}

@Composable
fun DL1Wrapper(
    navigationType: NavigationType,
    contentType: ContentType
) {
    val selectedDestination = route.INBOX
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    if (navigationType == NavigationType.PERMANENT) {
        PermanentNavigationDrawer(drawerContent = {
            ModalDrawerSheet {
                DL1PermanentNavigationDrawerContent(
                    selectedDestination = selectedDestination,
                    navigationContentPosition = NavigationContentPosition.CENTER
                )
            }
        }) {
            DL1AppContent()
        }
    } else {
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
                AnimatedVisibility(visible = navigationType == NavigationType.RAIL) {
                    Box(
                        Modifier
                            .background(Color.LightGray)
                            .width(50.dp)
                            .fillMaxHeight()
                    )
                }
                DL1AppContent()
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

object route {
    const val INBOX = "Inbox"
    const val ARTICLES = "Articles"
    const val DM = "DirectMessages"
    const val GROUPS = "Groups"
}

val TOP_LEVEL_DESTINATIONS = listOf(
    DL1TopLevelDestination(
        route = route.INBOX,
        selectedIcon = Icons.Default.Inbox,
        unselectedIcon = Icons.Default.Inbox,
        iconText = "Inbox"
    ),
    DL1TopLevelDestination(
        route = route.ARTICLES,
        selectedIcon = Icons.Default.Article,
        unselectedIcon = Icons.Default.Article,
        iconText = "Article"
    ),
    DL1TopLevelDestination(
        route = route.DM,
        selectedIcon = Icons.Outlined.ChatBubbleOutline,
        unselectedIcon = Icons.Outlined.ChatBubbleOutline,
        iconText = "Inbox"
    ),
    DL1TopLevelDestination(
        route = route.GROUPS,
        selectedIcon = Icons.Default.People,
        unselectedIcon = Icons.Default.People,
        iconText = "Article"
    )
)

@Composable
fun DL1PermanentNavigationDrawerContent(
    selectedDestination: String,
    navigationContentPosition: NavigationContentPosition
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
fun DL1AppContent() {
    Text("DashboardAppContent")
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