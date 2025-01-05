package com.oktaygenc.cinechoice.ui.presentation.cart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.oktaygenc.cinechoice.R
import com.oktaygenc.cinechoice.data.model.CardItem
import com.oktaygenc.cinechoice.utils.Constants.getImageUrl

@Composable
fun MovieItem(
    cartMovies: CardItem,
    onRemoveClick: (Int) -> Unit,
    onIncreaseClick: (Int) -> Unit,
    onDecreaseClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
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
                Text(
                    text = cartMovies.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
                Text(
                    text = "Category: ${cartMovies.category}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "Price: ${cartMovies.price}$",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    if (cartMovies.orderAmount > 1) {
                        IconButton(onClick = { onDecreaseClick(cartMovies.cartId) }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_decrease),
                                contentDescription = "Decrease",
                                tint = Color.Black
                            )
                        }
                    } else {
                        IconButton(onClick = { onRemoveClick(cartMovies.cartId) }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_delete),
                                contentDescription = "Delete",
                                tint = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = cartMovies.orderAmount.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { onIncreaseClick(cartMovies.cartId) }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_increase),
                            contentDescription = "Increase",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}

