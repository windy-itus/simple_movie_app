package com.example.simple_movie_app.ui.screen.movie_details.widgets

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString

@Composable
fun LinkText(url: String) {
    val context = LocalContext.current // Correctly capturing context
    ClickableText(
        text = AnnotatedString("Visit Homepage"),
        onClick = {
            // Intent to open URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent) // Start activity with the correct context
        },
        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
    )
}