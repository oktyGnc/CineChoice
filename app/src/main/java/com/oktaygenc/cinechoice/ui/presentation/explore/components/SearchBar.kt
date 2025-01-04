package com.oktaygenc.cinechoice.ui.presentation.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.oktaygenc.cinechoice.ui.presentation.explore.viewmodel.ExploreViewModel

@Composable
fun SearchBar(
    viewModel: ExploreViewModel, // ViewModel parametre olarak alınıyor
    modifier: Modifier = Modifier,
) {
    val searchQuery = viewModel.searchQuery.value

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { newQuery ->
            viewModel.onSearchQueryChanged(newQuery) // ViewModel'deki searchQuery'yi güncelliyoruz
        },
        label = { Text("Search") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = "Search Icon"
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colors.surface)
            .padding(10.dp),
        singleLine = true,
        textStyle = MaterialTheme.typography.body1,
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
            backgroundColor = MaterialTheme.colors.surface
        )
    )
}
