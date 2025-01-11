package com.oktaygenc.cinechoice.ui.presentation.cart.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.oktaygenc.cinechoice.ui.presentation.cart.components.MovieItem
import com.oktaygenc.cinechoice.ui.presentation.cart.viewmodel.CartScreenViewModel
import com.oktaygenc.cinechoice.ui.theme.SelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.TextSelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.TopAndBottomBarColor
import com.oktaygenc.cinechoice.ui.theme.TopBarColor
import com.oktaygenc.cinechoice.ui.theme.oswald

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController) {
    // ViewModel and state setup
    val viewModel: CartScreenViewModel = hiltViewModel()
    val cartMovies by viewModel.cartMovies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Total amount calculation
    val totalAmount = cartMovies.sumOf { it.price * it.orderAmount }

    // Fetch movies in cart on screen load
    LaunchedEffect(Unit) {
        viewModel.getMoviesInCart()
    }

    Scaffold(
        topBar = {
            // TopAppBar with back navigation
            TopAppBar(title = {
                Text(text = "Your Cart", fontFamily = oswald)
            },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = TopAndBottomBarColor),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TopBarColor
                        )
                    }
                })
        }, containerColor = Color.White

    ) { paddingValues ->

        Box {
            // If cart is empty and not loading, show no items message
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
                // If cart is not empty, display the list of movies
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
                                onRemoveClick = { movieId -> viewModel.deleteMovieFromCart(movieId) })
                        }
                    }

                    // Total amount and place order button
                    Row(
                        modifier = Modifier
                            .size(500.dp, 80.dp)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Amount:${totalAmount}$",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .size(200.dp, 40.dp)
                                .padding(start = 8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SelectedButtonColor)
                        ) {
                            Text(
                                text = "Place Order",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextSelectedButtonColor,
                            )
                        }
                    }
                }
            }

            // Show loading spinner while data is loading
            if (isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator(
                        color = SelectedButtonColor
                    )
                }
            }
        }
    }
}
