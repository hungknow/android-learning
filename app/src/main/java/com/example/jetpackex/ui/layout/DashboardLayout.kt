package com.example.jetpackex.ui.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
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

enum class NavigationType {
    RAIL, BOTTOM, PERMANENT
}

enum class NavigationContentPosition {
    TOP, CENTER
}

enum class ContentType {
    ONE_PANEL, DOUBLE_PANEL
}

val replyLightPrimary = Color(0xFF825500)
val replyLightOnPrimary = Color(0xFFFFFFFF)
val replyLightPrimaryContainer = Color(0xFFFFDDAE)
val replyLightOnPrimaryContainer = Color(0xFF2A1800)
val replyLightSecondary = Color(0xFF6F5B40)
val replyLightOnSecondary = Color(0xFFFFFFFF)
val replyLightSecondaryContainer = Color(0xFFFADEBC)
val replyLightOnSecondaryContainer = Color(0xFF271904)
val replyLightTertiary = Color(0xFF516440)
val replyLightOnTertiary = Color(0xFFFFFFFF)
val replyLightTertiaryContainer = Color(0xFFD3EABC)
val replyLightOnTertiaryContainer = Color(0xFF102004)
val replyLightError = Color(0xFFBA1B1B)
val replyLightErrorContainer = Color(0xFFFFDAD4)
val replyLightOnError = Color(0xFFFFFFFF)
val replyLightOnErrorContainer = Color(0xFF410001)
val replyLightBackground = Color(0xFFFCFCFC)
val replyLightOnBackground = Color(0xFF1F1B16)
val replyLightSurface = Color(0xFFFCFCFC)
val replyLightOnSurface = Color(0xFF1F1B16)
val replyLightSurfaceVariant = Color(0xFFF0E0CF)
val replyLightOnSurfaceVariant = Color(0xFF4F4539)
val replyLightOutline = Color(0xFF817567)
val replyLightInverseOnSurface = Color(0xFFF9EFE6)
val replyLightInverseSurface = Color(0xFF34302A)
val replyLightPrimaryInverse = Color(0xFFFFB945)

private val lightColorScheme = lightColorScheme(
    primary = replyLightPrimary,
    onPrimary = replyLightOnPrimary,
    primaryContainer = replyLightPrimaryContainer,
    onPrimaryContainer = replyLightOnPrimaryContainer,
    inversePrimary = replyLightPrimaryInverse,
    secondary = replyLightSecondary,
    onSecondary = replyLightOnSecondary,
    secondaryContainer = replyLightSecondaryContainer,
    onSecondaryContainer = replyLightOnSecondaryContainer,
    tertiary = replyLightTertiary,
    onTertiary = replyLightOnTertiary,
    tertiaryContainer = replyLightTertiaryContainer,
    onTertiaryContainer = replyLightOnTertiaryContainer,
    error = replyLightError,
    onError = replyLightOnError,
    errorContainer = replyLightErrorContainer,
    onErrorContainer = replyLightOnErrorContainer,
    background = replyLightBackground,
    onBackground = replyLightOnBackground,
    surface = replyLightSurface,
    onSurface = replyLightOnSurface,
    inverseSurface = replyLightInverseSurface,
    inverseOnSurface = replyLightInverseOnSurface,
    surfaceVariant = replyLightSurfaceVariant,
    onSurfaceVariant = replyLightOnSurfaceVariant,
    outline = replyLightOutline
)


val replyDarkPrimary = Color(0xFFFFB945)
val replyDarkOnPrimary = Color(0xFF452B00)
val replyDarkPrimaryContainer = Color(0xFF624000)
val replyDarkOnPrimaryContainer = Color(0xFFFFDDAE)
val replyDarkSecondary = Color(0xFFDDC3A2)
val replyDarkOnSecondary = Color(0xFF3E2E16)
val replyDarkSecondaryContainer = Color(0xFF56442B)
val replyDarkOnSecondaryContainer = Color(0xFFFADEBC)
val replyDarkTertiary = Color(0xFFB8CEA2)
val replyDarkOnTertiary = Color(0xFF243516)
val replyDarkTertiaryContainer = Color(0xFF3A4C2B)
val replyDarkOnTertiaryContainer = Color(0xFFD3EABC)
val replyDarkError = Color(0xFFFFB4A9)
val replyDarkErrorContainer = Color(0xFF930006)
val replyDarkOnError = Color(0xFF680003)
val replyDarkOnErrorContainer = Color(0xFFFFDAD4)
val replyDarkBackground = Color(0xFF1F1B16)
val replyDarkOnBackground = Color(0xFFEAE1D9)
val replyDarkSurface = Color(0xFF1F1B16)
val replyDarkOnSurface = Color(0xFFEAE1D9)
val replyDarkSurfaceVariant = Color(0xFF4F4539)
val replyDarkOnSurfaceVariant = Color(0xFFD3C4B4)
val replyDarkOutline = Color(0xFF9C8F80)
val replyDarkInverseOnSurface = Color(0xFF32281A)
val replyDarkInverseSurface = Color(0xFFEAE1D9)
val replyDarkPrimaryInverse = Color(0xFF624000)

private val replyDarkColorScheme = darkColorScheme(
    primary = replyDarkPrimary,
    onPrimary = replyDarkOnPrimary,
    primaryContainer = replyDarkPrimaryContainer,
    onPrimaryContainer = replyDarkOnPrimaryContainer,
    inversePrimary = replyDarkPrimaryInverse,
    secondary = replyDarkSecondary,
    onSecondary = replyDarkOnSecondary,
    secondaryContainer = replyDarkSecondaryContainer,
    onSecondaryContainer = replyDarkOnSecondaryContainer,
    tertiary = replyDarkTertiary,
    onTertiary = replyDarkOnTertiary,
    tertiaryContainer = replyDarkTertiaryContainer,
    onTertiaryContainer = replyDarkOnTertiaryContainer,
    error = replyDarkError,
    onError = replyDarkOnError,
    errorContainer = replyDarkErrorContainer,
    onErrorContainer = replyDarkOnErrorContainer,
    background = replyDarkBackground,
    onBackground = replyDarkOnBackground,
    surface = replyDarkSurface,
    onSurface = replyDarkOnSurface,
    inverseSurface = replyDarkInverseSurface,
    inverseOnSurface = replyDarkInverseOnSurface,
    surfaceVariant = replyDarkSurfaceVariant,
    onSurfaceVariant = replyDarkOnSurfaceVariant,
    outline = replyDarkOutline
)

@Composable
fun Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}


@Composable
fun DashboardLayout(
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

    DashboardLayoutWrapper(
        navigationType = navigationType,
        contentType = contentType
    )
}

@Composable
fun DashboardLayoutWrapper(
    navigationType: NavigationType,
    contentType: ContentType
) {
    if (navigationType == NavigationType.PERMANENT) {
        PermanentNavigationDrawer(drawerContent = {
            ModalDrawerSheet {
                PermanentNavigationDrawerContent(
                    selectedDestination = Route.INBOX,
                    navigationContentPosition = NavigationContentPosition.CENTER
                )
            }
        }) {

        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Text("Modal Drawer Text")
                }
            }

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
                Box(
                    Modifier
                        .border(5.dp, Color.DarkGray)
                        .fillMaxSize()
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

data class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: String
)

object Route {
    const val INBOX = "Inbox"
    const val ARTICLES = "Articles"
    const val DM = "DirectMessages"
    const val GROUPS = "Groups"
}

val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = Route.INBOX,
        selectedIcon = Icons.Default.Inbox,
        unselectedIcon = Icons.Default.Inbox,
        iconText = "Inbox"
    ),
    TopLevelDestination(
        route = Route.ARTICLES,
        selectedIcon = Icons.Default.Article,
        unselectedIcon = Icons.Default.Article,
        iconText = "Article"
    ),
    TopLevelDestination(
        route = Route.DM,
        selectedIcon = Icons.Outlined.ChatBubbleOutline,
        unselectedIcon = Icons.Outlined.ChatBubbleOutline,
        iconText = "Inbox"
    ),
    TopLevelDestination(
        route = Route.GROUPS,
        selectedIcon = Icons.Default.People,
        unselectedIcon = Icons.Default.People,
        iconText = "Article"
    )
)

@Composable
fun PermanentNavigationDrawerContent(
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
                        text = { Text("COMPOSE", textAlign = TextAlign.Center) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null
                            )
                        },
                        onClick = { /*TODO*/ })
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

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun InMobile() {
    DashboardLayout(
        windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(400.dp, 900.dp))
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 780, heightDp = 1024)
@Composable
fun InTablet() {
    DashboardLayout(
        windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(780.dp, 1024.dp))
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 1024, heightDp = 780)
@Composable
fun InDesktop() {
    Theme {
        DashboardLayout(
            windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(1024.dp, 780.dp))
        )
    }
}