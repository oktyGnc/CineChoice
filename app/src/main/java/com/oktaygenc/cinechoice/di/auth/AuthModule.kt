package com.oktaygenc.cinechoice.di.auth

import com.google.firebase.auth.FirebaseAuth
import com.oktaygenc.cinechoice.data.datasource.auth.AuthDataSource
import com.oktaygenc.cinechoice.data.repository.auth.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(firebaseAuth: FirebaseAuth): AuthDataSource {
        return AuthDataSource(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authDataSource: AuthDataSource): AuthRepository {
        return AuthRepository(authDataSource)
    }
}
