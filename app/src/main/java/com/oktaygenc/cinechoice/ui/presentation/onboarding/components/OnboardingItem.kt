package com.oktaygenc.cinechoice.ui.presentation.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingItem(page: Int) {
    val titles = listOf("Welcome", "Explore", "Get Started")
    val descriptions = listOf(
        "Welcome to our amazing app!",
        "Explore the features we offer.",
        "Get started with your journey!"
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = titles[page],
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = descriptions[page],
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}
