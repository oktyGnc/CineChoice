package com.oktaygenc.cinechoice.ui.presentation.onboarding.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.oktaygenc.cinechoice.ui.presentation.onboarding.components.OnboardingItem
import com.oktaygenc.cinechoice.ui.theme.SelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.TextSelectedButtonColor
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    // Remember the pager state to handle page changes
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    // Main layout for the onboarding screen
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // HorizontalPager to swipe through the onboarding items (pages)
        HorizontalPager(
            state = pagerState, modifier = Modifier.weight(1f)
        ) { page ->
            // Display the content of each page based on the current page index
            OnboardingItem(page = page)
        }

        // Row for the buttons (Skip and Next/Finish)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Show the "Skip" button on all pages except the last one (page 2)
            if (pagerState.currentPage != 2) {
                Button(
                    onClick = { onFinished() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SelectedButtonColor, contentColor = TextSelectedButtonColor
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = "Skip",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            // Spacer to align the buttons properly
            Spacer(modifier = Modifier.weight(1f))

            // Show the "Next" button unless it's the last page, in which case show "Finish"
            Button(
                onClick = {
                    if (pagerState.currentPage == 2) {
                        // If it's the last page, finish the onboarding
                        onFinished()
                    } else {
                        // Otherwise, move to the next page
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SelectedButtonColor, contentColor = TextSelectedButtonColor
                ),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = if (pagerState.currentPage == 2) "Finish" else "Next",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

