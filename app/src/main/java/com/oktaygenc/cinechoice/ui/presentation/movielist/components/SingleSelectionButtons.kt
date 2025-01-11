package com.oktaygenc.cinechoice.ui.presentation.movielist.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.oktaygenc.cinechoice.ui.theme.SelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.TextSelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.UnSelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.UnTextSelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.oswald

@Composable
fun SingleSelectionButtons(onCategorySelected: (String) -> Unit) {
    val categories = listOf("All", "Science Fiction", "Action", "Drama", "Fantastic")

    // Selected category state
    val selectedCategory = remember { mutableStateOf("All") } // "All" selected by default

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth() // Horizontal scroll
    ) {
        items(categories) { category ->
            Button(
                onClick = {
                    selectedCategory.value = category
                    onCategorySelected(category) // Notify the parent component
                },
                modifier = Modifier.padding(4.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = if (category == selectedCategory.value) Color.White else Color.Black,
                    backgroundColor = if (category == selectedCategory.value) SelectedButtonColor else UnSelectedButtonColor
                )
            ) {
                Text(text = category)
            }
        }
    }
}


