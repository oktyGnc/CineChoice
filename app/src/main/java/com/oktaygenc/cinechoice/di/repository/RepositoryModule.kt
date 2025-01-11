package com.oktaygenc.cinechoice.di.repository

import com.oktaygenc.cinechoice.data.datasource.movies.MoviesDataSource
import com.oktaygenc.cinechoice.data.repository.movie.MovieRepository
import com.oktaygenc.cinechoice.data.retrofit.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // Provides MoviesDataSource instance using MovieApi
    @Provides
    @Singleton
    fun provideMoviesDataSource(movieApi: MovieApi): MoviesDataSource {
        return MoviesDataSource(movieApi)
    }

    // Provides MovieRepository instance using MoviesDataSource
    @Provides
    @Singleton
    fun provideMoviesRepository(moviesDataSource: MoviesDataSource): MovieRepository {
        return MovieRepository(moviesDataSource)
    }
}
