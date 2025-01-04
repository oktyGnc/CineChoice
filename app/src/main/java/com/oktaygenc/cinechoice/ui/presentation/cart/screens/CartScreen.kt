package com.oktaygenc.cinechoice.ui.presentation.cart.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.oktaygenc.cinechoice.data.model.CardItem
import com.oktaygenc.cinechoice.ui.presentation.cart.viewmodel.CartScreenViewModel
import com.oktaygenc.cinechoice.utils.Constants.getImageUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController) {
    val viewModel: CartScreenViewModel = hiltViewModel()
    val cartMovies by viewModel.cartMovies.observeAsState(initial = emptyList())

    viewModel.getMoviesInCart()

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Your Cart", style = MaterialTheme.typography.titleLarge) })
    }) { paddingValues ->
        if (cartMovies.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No items in your cart.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(cartMovies) { movie ->
                    MovieItem(cartMovies = movie,
                        onRemoveClick = { /* Implement Remove Logic */ },
                        onDecreaseClick = {},
                        onIncreaseClick = {})
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    cartMovies: CardItem,
    onRemoveClick: (Int) -> Unit,
    onIncreaseClick: (Int) -> Unit,
    onDecreaseClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = getImageUrl(cartMovies.image),
                contentDescription = cartMovies.name,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = cartMovies.name, style = MaterialTheme.typography.titleLarge)
                Text(text = "Price: ${cartMovies.price}$", style = MaterialTheme.typography.bodyMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { onDecreaseClick(cartMovies.cartId) }) {
                        Text("-")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = cartMovies.orderAmount.toString(), style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onIncreaseClick(cartMovies.cartId) }) {
                        Text("+")
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { onRemoveClick }) {
                Text("Remove")
            }
        }
    }
}

