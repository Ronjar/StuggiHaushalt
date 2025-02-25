package com.robingebert.stuggihaushalt.common

import android.content.Context
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreen(url: String, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true // Falls die Webseite JS benötigt
                webViewClient = WebViewClient()  // Damit Links in der WebView geöffnet werden
                loadUrl(url)
            }
        },
        modifier = modifier.fillMaxSize()
    )
}


fun openCustomTab(context: Context, url: String) {
    val intent = CustomTabsIntent.Builder()
        .setShowTitle(true)
        .setToolbarColor(Color(0xFF6200EE).value.toInt())
        .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
        .build()
    intent.launchUrl(context, Uri.parse(url))
}