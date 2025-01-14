package com.oktaygenc.cinechoice.presentation.screens.explore.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import com.oktaygenc.cinechoice.presentation.common.theme.SelectedButtonColor
import com.oktaygenc.cinechoice.presentation.common.theme.TextSelectedButtonColor
import com.oktaygenc.cinechoice.utils.Constants.getImageUrl

@Composable
fun MovieCardForExplore(
    movie: Movie,
    modifier: Modifier = Modifier,
    onNavigateDetail: (Movie) -> Unit,
) {
    // Card that represents the movie item
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clickable { onNavigateDetail(movie) }, // Navigate on click
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = TextSelectedButtonColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Display movie image
            AsyncImage(
                model = getImageUrl(movie.image),
                contentDescription = movie.name,
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 12.dp)
            )

            // Column for movie details (name, category, rating)
            Column(
                verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movie.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = movie.category, style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = SelectedButtonColor
                    )
                    Text(
                        text = movie.rating.toString(), style = MaterialTheme.typography.bodyMedium
                    )
                }

            }
        }
    }
}
