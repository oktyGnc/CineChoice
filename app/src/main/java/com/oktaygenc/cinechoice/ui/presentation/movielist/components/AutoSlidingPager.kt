package com.oktaygenc.cinechoice.ui.presentation.movielist.components

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
    val actualPageCount = 4

    val pagerState = rememberPagerState(
        pageCount = { actualPageCount },
        initialPage = 0
    )

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        while(true) {
            delay(4000L)
            coroutineScope.launch {
                val nextPage = (pagerState.currentPage + 1) % actualPageCount
                pagerState.animateScrollToPage(page = nextPage)
            }
        }
    }

    // Ana container Box
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Pager Box
        Box(
            modifier = Modifier
                .width(320.dp)
                .height(380.dp)
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .background(Color.White),
                state = pagerState,
                pageSpacing = 10.dp,
            ) { page ->
                Box(
                    modifier = Modifier
                        .width(320.dp)
                        .height(380.dp)
                ) {
                    PagerItem(
                        page = page,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .width(320.dp)
                            .height(380.dp)
                            .padding(horizontal = 16.dp)
                    )
                }
            }

            Row(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(actualPageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) {
                        Color.White
                    } else {
                        Color.White.copy(alpha = 0.5f)
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