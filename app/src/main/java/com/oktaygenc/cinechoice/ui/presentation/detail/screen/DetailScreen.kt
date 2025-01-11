package com.oktaygenc.cinechoice.ui.presentation.detail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.oktaygenc.cinechoice.R
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
import com.oktaygenc.cinechoice.ui.presentation.detail.viewmodel.DetailScreenViewModel
import com.oktaygenc.cinechoice.ui.theme.SelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.TextGray
import com.oktaygenc.cinechoice.ui.theme.TextSelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.TopAndBottomBarColor
import com.oktaygenc.cinechoice.ui.theme.TopBarColor
import com.oktaygenc.cinechoice.ui.theme.UnTextSelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.oswald
import com.oktaygenc.cinechoice.utils.Constants.getImageUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, comingMovie: Movie) {
    val viewModel: DetailScreenViewModel = hiltViewModel()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle() // Collect favorite state
    var orderCount by remember { mutableStateOf(1) } // Track the order count for the movie

    // Check if the movie is already marked as a favorite when the screen is launched
    LaunchedEffect(comingMovie.id) {
        viewModel.checkFavoriteMovie(comingMovie.id)
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = comingMovie.name, color = TopBarColor, fontFamily = oswald) // Movie name as title
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = TopAndBottomBarColor),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) { // Back button
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TopBarColor
                    )
                }
            },
            actions = {
                // Favorite button
                IconButton(onClick = { viewModel.toggleFavorite(comingMovie) }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (isFavorite) SelectedButtonColor else TopBarColor
                    )
                }
            }
        )
    }) { paddingValues ->
        // Movie details screen content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Movie image
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                AsyncImage(
                    model = getImageUrl(comingMovie.image),
                    contentDescription = comingMovie.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp, 225.dp)
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Movie information
            Text(text = comingMovie.name, style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold, color = UnTextSelectedButtonColor)
            Text(text = "Category: ${comingMovie.category}", style = MaterialTheme.typography.body2, color = TextGray, fontWeight = FontWeight.Bold)
            Text(text = "Price: \$${comingMovie.price}", style = MaterialTheme.typography.body2, color = TextGray, fontWeight = FontWeight.Bold)
            Text(text = "Rating: ${comingMovie.rating}", style = MaterialTheme.typography.body2, color = TextGray, fontWeight = FontWeight.Bold)
            Text(text = "Year: ${comingMovie.year}", style = MaterialTheme.typography.body2, color = TextGray, fontWeight = FontWeight.Bold)
            Text(text = "Director: ${comingMovie.director}", style = MaterialTheme.typography.body2, color = TextGray, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = comingMovie.description, color = UnTextSelectedButtonColor, modifier = Modifier.padding(vertical = 8.dp))

            // Order Count Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Decrease button
                IconButton(
                    onClick = { if (orderCount > 1) orderCount-- },
                    modifier = Modifier
                        .size(40.dp)
                        .background(color = TopAndBottomBarColor, shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_decrease),
                        contentDescription = "Decrease count",
                        tint = SelectedButtonColor
                    )
                }

                // Order count display
                Text(
                    text = orderCount.toString(),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Increase button
                IconButton(
                    onClick = { orderCount++ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(color = TopAndBottomBarColor, shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_increase),
                        contentDescription = "Increase count",
                        tint = SelectedButtonColor
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Add to Cart Button
            Button(
                onClick = {
                    navController.navigate("cart") // Navigate to cart screen
                    viewModel.addMovieToCart(
                        name = comingMovie.name,
                        image = comingMovie.image,
                        price = comingMovie.price,
                        category = comingMovie.category,
                        rating = comingMovie.rating,
                        year = comingMovie.year,
                        director = comingMovie.director,
                        description = comingMovie.description,
                        orderAmount = orderCount // Using the order count value
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = SelectedButtonColor)
            ) {
                Text(
                    text = "Add to Cart ($orderCount)", // Show the number of items being added
                    color = TextSelectedButtonColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
