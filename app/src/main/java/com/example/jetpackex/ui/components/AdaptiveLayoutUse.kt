package com.example.jetpackex.ui.components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WindowSizeClassUse(
    widthSizeClass: WindowWidthSizeClass
) {
    if (widthSizeClass == WindowWidthSizeClass.Compact) {
        Text("hello")
    } else {
        Text("not hello")
    }

}

fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//@Preview(showBackground = true)
@Preview(showBackground = true, device = Devices.PHONE)
//@Preview(showBackground = true, device = Devices.TABLET)
//@Preview(showBackground = true, device = Devices.FOLDABLE)
@Composable
fun PreviewWindowSizeClassUse() {
    WindowSizeClassUse(WindowWidthSizeClass.Compact)
}