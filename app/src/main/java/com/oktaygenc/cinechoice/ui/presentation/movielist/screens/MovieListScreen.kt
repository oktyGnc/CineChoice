package com.oktaygenc.cinechoice.ui.presentation.movielist.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oktaygenc.cinechoice.data.model.Movie
import com.oktaygenc.cinechoice.ui.presentation.movielist.components.AutoSlidingPager
import com.oktaygenc.cinechoice.ui.presentation.movielist.components.MovieCard
import com.oktaygenc.cinechoice.ui.presentation.movielist.components.SingleSelectionButtons
import com.oktaygenc.cinechoice.ui.presentation.movielist.state.MoviesAction
import com.oktaygenc.cinechoice.ui.presentation.movielist.state.MoviesState
import com.oktaygenc.cinechoice.ui.theme.DeleteColor
import com.oktaygenc.cinechoice.ui.theme.TopAndBottomBarColor
import com.oktaygenc.cinechoice.ui.theme.TopBarColor
import com.oktaygenc.cinechoice.ui.theme.lobster

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    state: MoviesState,
    onNavigateCart: () -> Unit,
    onNavigateDetail: (Movie) -> Unit,
    onAction: (MoviesAction) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollState = rememberLazyGridState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = TopAndBottomBarColor),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "CineChoice",
                            color = TopBarColor,
                            fontFamily = lobster,
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onNavigateCart()
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = DeleteColor
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .background(Color.White)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.error != null -> {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyVerticalGrid(
                        state = scrollState,
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Add pager as the first item spanning all columns
                        item(span = { GridItemSpan(2) }) {
                            AutoSlidingPager()
                        }


                        item(span = { GridItemSpan(2) }) {
                            SingleSelectionButtons(
                                onCategorySelected = { category ->
                                    onAction(MoviesAction.OnCategoryClick(category))
                                }
                            )
                        }

                        // Add filtered movie items
                        items(state.filteredMovies) { movie ->
                            MovieCard(
                                movie = movie,
                                onAddToCartClick = {
                                    onNavigateCart()
                                    onAction(MoviesAction.OnCartClick(movie))
                                },
                                onClick = { onNavigateDetail(movie) }
                            )
                        }
                    }
                }
            }
        }
    }
}
