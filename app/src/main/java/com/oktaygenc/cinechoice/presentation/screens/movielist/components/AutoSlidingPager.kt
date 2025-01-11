package com.oktaygenc.cinechoice.presentation.screens.movielist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AutoSlidingPager() {
    val actualPageCount = 4 // Total pages in the pager

    val pagerState = rememberPagerState(
        pageCount = { actualPageCount }, // Set page count
        initialPage = 0 // Start from the first page
    )

    val coroutineScope = rememberCoroutineScope()

    // Automatic sliding of pages
    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(4000L) // Wait for 4 seconds
            coroutineScope.launch {
                val nextPage = (pagerState.currentPage + 1) % actualPageCount // Get next page
                pagerState.animateScrollToPage(page = nextPage) // Animate page change
            }
        }
    }

    // Main container Box for layout
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        // Box for Pager UI
        Box(
            modifier = Modifier
                .width(320.dp)
                .height(380.dp)
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .background(Color.White), // Pager background
                state = pagerState,
                pageSpacing = 10.dp,
            ) { page ->
                Box(
                    modifier = Modifier
                        .width(320.dp)
                        .height(380.dp)
                ) {
                    PagerItem( // Custom Pager item content
                        page = page,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .width(320.dp)
                            .height(380.dp)
                            .padding(horizontal = 16.dp)
                    )
                }
            }

            // Page indicator dots at the bottom
            Row(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(actualPageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) {
                        Color.White // Active page indicator
                    } else {
                        Color.White.copy(alpha = 0.5f) // Inactive page indicator
                    }
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(color = color, shape = RoundedCornerShape(50))
                    )
                }
            }
        }
    }
}
