package com.oktaygenc.cinechoice.ui.presentation.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.oktaygenc.cinechoice.R

@Composable
fun OnboardingItem(page: Int) {
    // List of titles for each onboarding page
    val titles = listOf(
        "Welcome",
        "Explore",
        "Get Started"
    )

    // List of descriptions for each onboarding page
    val descriptions = listOf(
        "Welcome to CineChoice! Let Movie Time Begin!",
        "Up to 50% Off Every Movie at CineChoice!",
        "Are you ready? Buckle Up, Let the Movie Start!"
    )

    // Load the corresponding Lottie animation based on the page index
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            when (page) {
                0 -> R.raw.welcome_animation // First page animation
                1 -> R.raw.explore_animation // Second page animation
                else -> R.raw.start_animation // Third page animation
            }
        )
    )

    // Control the animation progress and make it loop infinitely
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    // Layout for the onboarding item
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the Lottie animation
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.size(300.dp) // Set the size of the animation
        )

        Spacer(modifier = Modifier.height(32.dp)) // Space between animation and text

        // Display the title for the current page
        Text(
            text = titles[page],
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp)) // Space between title and description

        // Display the description for the current page
        Text(
            text = descriptions[page],
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

