package com.oktaygenc.cinechoice.ui.presentation.movielist.screen

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
import com.oktaygenc.cinechoice.data.model.entitiy.Movie
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
    // Create scroll behavior for the TopAppBar
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // Create a scroll state for the LazyGrid
    val scrollState = rememberLazyGridState()

    Scaffold(
        topBar = {
            // TopAppBar with a title and cart icon
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = TopAndBottomBarColor),
                title = {
                    // Box containing the app name centered in the TopAppBar
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
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
                    // Cart button in the TopAppBar
                    IconButton(onClick = onNavigateCart) {
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
        // Box wrapping the main content of the screen
        Box(
            modifier = Modifier
                .fillMaxSize() // Take up the full screen
                .padding(paddingValues) // Apply padding from Scaffold
                .nestedScroll(scrollBehavior.nestedScrollConnection) // Connect to the scroll behavior
                .background(Color.White) // White background
        ) {
            // Display loading spinner or error message based on the state
            when {
                state.isLoading -> {
                    // Show a loading spinner while data is being fetched
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.error != null -> {
                    // Show error message if there was an error fetching data
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    // Show filtered movie items in a grid when data is loaded successfully
                    LazyVerticalGrid(
                        state = scrollState,
                        columns = GridCells.Fixed(2), // Use two columns in the grid
                        contentPadding = PaddingValues(8.dp), // Padding around the grid
                        horizontalArrangement = Arrangement.spacedBy(20.dp), // Spacing between columns
                        verticalArrangement = Arrangement.spacedBy(20.dp), // Spacing between rows
                        modifier = Modifier.fillMaxSize() // Fill the entire screen
                    ) {
                        // First item: AutoSlidingPager which spans both columns
                        item(span = { GridItemSpan(2) }) {
                            AutoSlidingPager()
                        }

                        // Second item: Category selection buttons that span both columns
                        item(span = { GridItemSpan(2) }) {
                            SingleSelectionButtons(
                                onCategorySelected = { category ->
                                    onAction(MoviesAction.OnCategoryClick(category)) // Notify parent component of category selection
                                }
                            )
                        }

                        // Display each filtered movie item
                        items(state.filteredMovies) { movie ->
                            MovieCard(
                                movie = movie,
                                onAddToCartClick = {
                                    onNavigateCart() // Navigate to cart
                                    onAction(MoviesAction.OnCartClick(movie)) // Add movie to cart
                                },
                                onClick = { onNavigateDetail(movie) } // Navigate to movie detail page
                            )
                        }
                    }
                }
            }
        }
    }
}

