package com.oktaygenc.cinechoice.ui.presentation.cart.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oktaygenc.cinechoice.data.model.entitiy.CardItem
import com.oktaygenc.cinechoice.domain.model.CartItemModel
import com.oktaygenc.cinechoice.usecase.AddMovieToCartUseCase
import com.oktaygenc.cinechoice.usecase.DeleteMovieFromCartUseCase
import com.oktaygenc.cinechoice.usecase.GetMoviesInCartUseCase
import com.oktaygenc.cinechoice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val getMoviesInCartUseCase: GetMoviesInCartUseCase,
    private val deleteMovieFromCartUseCase: DeleteMovieFromCartUseCase,
) : ViewModel() {
    private val _cartMovies = MutableStateFlow<List<CartItemModel>>(emptyList())
    val cartMovies: StateFlow<List<CartItemModel>> get() = _cartMovies.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getMoviesInCart()
    }

    fun getMoviesInCart() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val resource = getMoviesInCartUseCase.invoke()) {
                is Resource.Success -> _cartMovies.value = resource.data
                is Resource.Error -> {
                    Log.e(
                        "CartViewModel", "Error getting movies in cart: ${resource.message}"
                    )
                    _cartMovies.value = emptyList()
                }
                else -> Unit
            }
            _isLoading.value = false
        }
    }

    fun deleteMovieFromCart(cartIdList: List<Int>) {
        viewModelScope.launch {
            _isLoading.value = true
            cartIdList.forEach { cartId ->
                when (val resource = deleteMovieFromCartUseCase.invoke(cartId)) {
                    is Resource.Success -> Log.d("CartViewModel", "Movie deleted from cart: ${resource.data}")
                    is Resource.Error -> Log.e("CartViewModel", "Error deleting movie from cart: ${resource.message}")
                    else -> Unit
                }
                _isLoading.value = false
            }
            getMoviesInCart()
        }
    }
}






