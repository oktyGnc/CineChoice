package com.oktaygenc.cinechoice.ui.presentation.favorite.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import com.oktaygenc.cinechoice.ui.theme.SelectedButtonColor
import com.oktaygenc.cinechoice.utils.Constants.getImageUrl

@Composable
fun FavoriteCard(
    movie: Movie, // Movie data to display
    onRemoveClick: () -> Unit, // Action to remove the movie from favorites
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface // Card background color
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = getImageUrl(movie.image), // Fetch and display movie image
                contentDescription = movie.name,
                modifier = Modifier
                    .size(60.dp,75.dp) // Set image size
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop // Crop image to fit the size
            )

            // Movie details Section
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f) // To make the text stretch in the remaining space
            ) {
                Text(
                    text = movie.name,
                    fontSize = 18.sp,
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
                        imageVector = Icons.Default.Star, // Display rating star icon
                        contentDescription = "Remove from favorites",
                        modifier = Modifier.size(20.dp),
                        tint = SelectedButtonColor // Icon color
                    )
                    Text(
                        text = movie.rating.toString(), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold
                    )
                }
            }

            IconButton(
                onClick = onRemoveClick, // Handle remove from favorites action
                modifier = Modifier.align(Alignment.CenterVertically).size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete, // Display delete icon
                    contentDescription = "Remove from favorites",
                    tint = SelectedButtonColor,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }
}
