package com.oktaygenc.cinechoice.ui.presentation.cart.state


import com.oktaygenc.cinechoice.data.model.entitiy.CardItem

data class CartState(
    val cartMovies: List<CardItem> = emptyList(),  // Sepetteki filmler için boş liste
    val isLoading: Boolean = false,  // Yükleniyor durumu
    val error: String? = null,  // Hata mesajı
    val successMessage: String? = null,  // Başarı mesajı
    val success: Int = 0  // Başarı durumu
)




