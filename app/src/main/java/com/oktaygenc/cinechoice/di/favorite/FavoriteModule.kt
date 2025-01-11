package com.oktaygenc.cinechoice.di.favorite

import com.google.firebase.firestore.FirebaseFirestore
import com.oktaygenc.cinechoice.data.datasource.favorite.FavoriteDataSource
import com.oktaygenc.cinechoice.data.datasource.auth.AuthDataSource
import com.oktaygenc.cinechoice.data.repository.favorite.FavoriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteModule {

    // Provides Firestore instance
    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    // Provides FavoriteRepository with FavoriteDataSource and AuthDataSource
    @Provides
    @Singleton
    fun provideFavoriteRepository(
        favoriteDataSource: FavoriteDataSource,
        authDataSource: AuthDataSource,
    ): FavoriteRepository {
        return FavoriteRepository(favoriteDataSource, authDataSource)
    }

    // Provides FavoriteDataSource with Firestore
    @Provides
    @Singleton
    fun provideFavoriteDataSource(firestore: FirebaseFirestore): FavoriteDataSource {
        return FavoriteDataSource(firestore)
    }
}
