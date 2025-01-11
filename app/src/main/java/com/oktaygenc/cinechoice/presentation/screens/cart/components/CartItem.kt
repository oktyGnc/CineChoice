package com.oktaygenc.cinechoice.presentation.screens.cart.components

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
import com.oktaygenc.cinechoice.domain.model.CartItemModel
import com.oktaygenc.cinechoice.presentation.common.theme.SelectedButtonColor
import com.oktaygenc.cinechoice.presentation.common.theme.oswald
import com.oktaygenc.cinechoice.utils.Constants.getImageUrl

@Composable
fun MovieItem(
    cartMovies: CartItemModel,
    onRemoveClick: (List<Int>) -> Unit,
) {
    // Calculate total price for the cart item
    var cardPriceCheck = cartMovies.price * cartMovies.orderAmount

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            // Display movie image
            AsyncImage(
                model = getImageUrl(cartMovies.image),
                contentDescription = cartMovies.name,
                modifier = Modifier
                    .size(100.dp, 130.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                // Movie name and other details
                Text(
                    text = cartMovies.name,
                    fontFamily = oswald,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
                Text(
                    text = "Category: ${cartMovies.category}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "Price: ${cardPriceCheck}$", fontFamily = oswald, color = Color.Black
                )
                Text(
                    text = "Order Amount: ${cartMovies.orderAmount}",
                    fontFamily = oswald,
                    color = Color.Black
                )
            }
            // Remove movie from cart
            IconButton(
                onClick = { onRemoveClick(cartMovies.cartIdList) },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Remove",
                    tint = SelectedButtonColor,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}
