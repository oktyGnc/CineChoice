package com.oktaygenc.cinechoice.ui.presentation.cart.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.oktaygenc.cinechoice.R
import com.oktaygenc.cinechoice.data.model.entitiy.CardItem
import com.oktaygenc.cinechoice.utils.Constants.getImageUrl

@Composable
fun MovieItem(
    cartMovies: CardItem,
    onRemoveClick: (Int) -> Unit,
) {
    Log.d("MovieItemddebug", "OrderAmount for ${cartMovies.name}: ${cartMovies.orderAmount}")
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
                modifier = Modifier
                    .size(100.dp, 130.dp) // Boyut ayarı
                    .clip(RoundedCornerShape(16.dp))
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
                Text(
                    text = "Order Amount: ${cartMovies.orderAmount}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
            IconButton(
                onClick = { onRemoveClick(cartMovies.cartId) },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Remove",
                    tint = Color.Red
                )
            }
        }
    }
}

