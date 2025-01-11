package com.oktaygenc.cinechoice.ui.presentation.movielist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.oktaygenc.cinechoice.R

@Composable
fun PagerItem(page: Int, modifier: Modifier = Modifier) {
    // List of images
    val images = listOf(
        R.drawable.discountimage,
        R.drawable.filmimage1,
        R.drawable.filmimage2,
        R.drawable.filmimage3
    )

    Surface(
        modifier = modifier, color = Color.DarkGray, shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Display the image
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = "Slide image $page",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}
