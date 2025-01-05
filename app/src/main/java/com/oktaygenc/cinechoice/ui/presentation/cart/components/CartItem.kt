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
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = getImageUrl(cartMovies.image),
                contentDescription = cartMovies.name,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = cartMovies.name, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = "Price: ${cartMovies.price}$",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Total: ${cartMovies.price * cartMovies.orderAmount}$",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (cartMovies.orderAmount > 1) {
                        IconButton(onClick = { onDecreaseClick(cartMovies.cartId) }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_decrease),
                                contentDescription = "Decrease"
                            )
                        }
                    } else {
                        IconButton(onClick = { onRemoveClick(cartMovies.cartId) }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_delete),
                                contentDescription = "Delete"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = cartMovies.orderAmount.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { onIncreaseClick(cartMovies.cartId) }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_increase),
                            contentDescription = "Increase"
                        )
                    }
                }
            }
        }
    }
}