package com.oktaygenc.cinechoice.ui.presentation.movielist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.oktaygenc.cinechoice.data.model.Movie
import com.oktaygenc.cinechoice.utils.Constants.getImageUrl


@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onAddToCartClick: () -> Unit = {},
) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(modifier = modifier
        .clickable { onClick() }
        .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
        Box {
            AsyncImage(
                model = getImageUrl(movie.image),
                contentDescription = movie.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp, 225.dp)
            )

            Surface(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd),
                color = Color.Red,
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = "50% OFF",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = {
                    isFavorite = !isFavorite
                    onFavoriteClick()
                }, modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = movie.name,
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFFFFB800)
                )
                Text(
                    text = movie.rating.toString(), fontSize = 15.sp, color = Color.Gray
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "$${movie.price * 2}", style = TextStyle(
                            textDecoration = TextDecoration.LineThrough,
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    )
                    Text(
                        text = "$${movie.price}", fontSize = 20.sp, fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = { onAddToCartClick() }, modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Add to Cart",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}