package com.oktaygenc.cinechoice.ui.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import com.oktaygenc.cinechoice.usecase.AddMovieToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val addMovieToCartUseCase: AddMovieToCartUseCase) :
    ViewModel() {
    fun addMovieToCart(
        name: String,
        image: String,
        price: Int,
        category: String,
        rating: Double,
        year: Int,
        director: String,
        description: String,
        orderAmount: Int,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            addMovieToCartUseCase.invoke(
                name,
                image,
                price,
                category,
                rating,
                year,
                director,
                description,
                orderAmount,
            )
        }
    }
}