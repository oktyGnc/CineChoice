package com.oktaygenc.cinechoice.retrofit

import com.oktaygenc.cinechoice.data.model.AddDeleteResponse
import com.oktaygenc.cinechoice.data.model.CartApiResponse
import com.oktaygenc.cinechoice.data.model.MoviesApiResponse
import com.oktaygenc.cinechoice.utils.UserSessionManager
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MovieApi {
    // Tüm Filmleri Getir
    @GET("movies/getAllMovies.php")
    suspend fun getAllMovies(): MoviesApiResponse

    // Kullanıcının Sepetindeki Filmleri Getir
    @POST("movies/getMovieCart.php")
    @FormUrlEncoded
    suspend fun getMoviesInCart(
        @Field("userName") userName: String = UserSessionManager.getCurrentUser()
    ): CartApiResponse

    // Sepete Film Ekle
    @POST("movies/insertMovie.php")
    @FormUrlEncoded
    suspend fun addMovieToCart(
        @Field("name") name: String,
        @Field("image") image: String,
        @Field("price") price: Int,
        @Field("category") category: String,
        @Field("rating") rating: Double,
        @Field("year") year: Int,
        @Field("director") director: String,
        @Field("description") description: String,
        @Field("orderAmount") orderAmount: Int,
        @Field("userName") userName: String = UserSessionManager.getCurrentUser()
    ): AddDeleteResponse

    // Sepetten Film Sil
    @POST("movies/deleteMovie.php")
    @FormUrlEncoded
    suspend fun deleteMovieFromCart(
        @Field("cartId") cartId: Int,
        @Field("userName") userName: String = UserSessionManager.getCurrentUser()
    ): AddDeleteResponse
}
