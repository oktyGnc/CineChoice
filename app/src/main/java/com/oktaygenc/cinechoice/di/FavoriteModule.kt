package com.oktaygenc.cinechoice.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.oktaygenc.cinechoice.data.datasource.FavoriteDataSource
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
    fun provideFavoriteRepository(favoriteDataSource: FavoriteDataSource): FavoriteRepository {
        return FavoriteRepository(favoriteDataSource)
    }

    @Provides
    @Singleton
    fun provideFavoriteDataSource(favoriteDataSource: FavoriteDataSource): FavoriteDataSource {
        return favoriteDataSource
    }

    @Provides
    @Singleton
    fun provideFirebaseCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("users")

    }

}