package com.oktaygenc.cinechoice.di.repository

import com.oktaygenc.cinechoice.data.datasource.MoviesDataSource
import com.oktaygenc.cinechoice.data.repository.MovieRepository
import com.oktaygenc.cinechoice.retrofit.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMoviesDataSource(movieApi: MovieApi): MoviesDataSource {
        return MoviesDataSource(movieApi)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(moviesDataSource: MoviesDataSource): MovieRepository {
        return MovieRepository(moviesDataSource)
    }
}