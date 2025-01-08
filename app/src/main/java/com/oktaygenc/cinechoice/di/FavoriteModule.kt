package com.oktaygenc.cinechoice.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.oktaygenc.cinechoice.data.datasource.FavoriteDataSource
import com.oktaygenc.cinechoice.data.datasource.auth.AuthDataSource
import com.oktaygenc.cinechoice.data.repository.FavoriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        favoriteDataSource: FavoriteDataSource,
        authDataSource: AuthDataSource,
    ): FavoriteRepository {
        return FavoriteRepository(favoriteDataSource, authDataSource)
    }

    @Provides
    @Singleton
    fun provideFavoriteDataSource(firestore: FirebaseFirestore): FavoriteDataSource {
        return FavoriteDataSource(firestore)
    }
}