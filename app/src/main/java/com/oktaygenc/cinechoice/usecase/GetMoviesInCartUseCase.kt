package com.oktaygenc.cinechoice.usecase

import com.oktaygenc.cinechoice.data.model.CardItem
import com.oktaygenc.cinechoice.data.repository.MovieRepository
import com.oktaygenc.cinechoice.utils.Resource
import javax.inject.Inject

class GetMoviesInCartUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): Resource<List<CardItem>> {
        return repository.getMoviesInCart()
    }
}