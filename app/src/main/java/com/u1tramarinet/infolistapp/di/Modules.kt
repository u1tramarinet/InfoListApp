package com.u1tramarinet.infolistapp.di

import com.u1tramarinet.infolistapp.repository.InfoRepository
import com.u1tramarinet.infolistapp.repository.InfoRepositoryImpl
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
    abstract fun bindsInfoRepository(impl: InfoRepositoryImpl): InfoRepository
}