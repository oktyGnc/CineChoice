package com.oktaygenc.cinechoice.ui.presentation.cart.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.oktaygenc.cinechoice.ui.presentation.cart.components.MovieItem
import com.oktaygenc.cinechoice.ui.presentation.cart.viewmodel.CartScreenViewModel
import com.oktaygenc.cinechoice.ui.theme.TopAndBottomBarColor
import com.oktaygenc.cinechoice.ui.theme.TopBarColor
import com.oktaygenc.cinechoice.ui.theme.oswald

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController) {
    val viewModel: CartScreenViewModel = hiltViewModel()
    val cartMovies by viewModel.cartMovies.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState()

    // Toplam tutarı hesaplayan fonksiyon
    val totalAmount = cartMovies.sumOf { it.price * it.orderAmount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Your Cart", fontFamily = oswald)
                }, colors = TopAppBarDefaults.topAppBarColors(containerColor = TopAndBottomBarColor),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TopBarColor
                        )
                    }
                }
            )

        }, containerColor = Color.White

    ) { paddingValues ->
        Box {
            if (cartMovies.isEmpty() && !isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No items in your cart.")
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        items(cartMovies) { movie ->
                            MovieItem(cartMovies = movie,
                                onRemoveClick = { movieId -> viewModel.deleteMovieFromCart(movieId) },
                                onDecreaseClick = { movieId -> viewModel.deleteMovieFromCart(movieId) },
                                onIncreaseClick = {
                                    viewModel.addMovieToCart(
                                        name = movie.name,
                                        image = movie.image,
                                        price = movie.price,
                                        category = movie.category,
                                        rating = movie.rating,
                                        year = movie.year,
                                        director = movie.director,
                                        description = movie.description,
                                        orderAmount = movie.orderAmount
                                    )
                                })
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Total Amount:", style = MaterialTheme.typography.titleMedium)
                        Text(text = "$$totalAmount", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }

            if (isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}