package com.oktaygenc.cinechoice.presentation.screens.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileDetail(label: String, value: String) {
    // Column to hold label and value text
    Column(modifier = Modifier.fillMaxWidth()) {
        // Label text with bold styling and adjusted font size
        Text(
            text = label, // Label text ("Username", "Email")
            style = MaterialTheme.typography.caption.copy(
                fontWeight = FontWeight.Bold, // Bold font weight for the label
                fontSize = 16.sp // Font size for the label
            )
        )

        // Spacer between the label and value
        Spacer(modifier = Modifier.height(4.dp))

        // Value text (e.g., actual username or email)
        Text(
            text = value, // Value associated with the label (e.g., actual username)
            style = MaterialTheme.typography.body1 // Body style for the value text
        )
    }
}
