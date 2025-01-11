package com.oktaygenc.cinechoice.data.retrofit

import com.oktaygenc.cinechoice.data.model.response.AddDeleteResponse
import com.oktaygenc.cinechoice.data.model.response.CartApiResponse
import com.oktaygenc.cinechoice.data.model.response.MoviesApiResponse
import com.oktaygenc.cinechoice.utils.UserSessionManager
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MovieApi {
    // Sends a GET request to fetch all movies.
    @GET("movies/getAllMovies.php")
    suspend fun getAllMovies(): MoviesApiResponse

    // Sends a POST request to fetch movies in the user's cart.
    @POST("movies/getMovieCart.php")
    @FormUrlEncoded
    suspend fun getMoviesInCart(
        // Retrieves the username (from UserSessionManager).
        @Field("userName") userName: String = UserSessionManager.getCurrentUser(),
    ): CartApiResponse

    // Sends a POST request to add a movie to the cart.
    @POST("movies/insertMovie.php")
    @FormUrlEncoded
    suspend fun addMovieToCart(
        // Sends the movie details (name, image, price, category, etc.) as form data.
        @Field("name") name: String,
        @Field("image") image: String,
        @Field("price") price: Int,
        @Field("category") category: String,
        @Field("rating") rating: Double,
        @Field("year") year: Int,
        @Field("director") director: String,
        @Field("description") description: String,
        @Field("orderAmount") orderAmount: Int,
        // Retrieves the username (from UserSessionManager).
        @Field("userName") userName: String = UserSessionManager.getCurrentUser(),
    ): AddDeleteResponse

    // Sends a POST request to delete a movie from the cart.
    @POST("movies/deleteMovie.php")
    @FormUrlEncoded
    suspend fun deleteMovieFromCart(
        // Sends the cart ID of the movie to be deleted.
        @Field("cartId") cartId: Int,
        // Retrieves the username (from UserSessionManager).
        @Field("userName") userName: String = UserSessionManager.getCurrentUser(),
    ): AddDeleteResponse
}

