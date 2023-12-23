package com.u1tramarinet.infolistapp.di

import com.u1tramarinet.infolistapp.repository.RandomValuesRepository
import com.u1tramarinet.infolistapp.repository.RandomValuesRepositoryImpl
import com.u1tramarinet.infolistapp.repository.RandomValueRepository
import com.u1tramarinet.infolistapp.repository.RandomValueRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Modules {
    @Binds
    @Singleton
    abstract fun bindsRandomValueRepository(impl: RandomValueRepositoryImpl): RandomValueRepository

    @Binds
    @Singleton
    abstract fun bindsRandomValuesRepository(impl: RandomValuesRepositoryImpl): RandomValuesRepository
}