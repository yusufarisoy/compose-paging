package com.yusufarisoy.composepagingapp.di

import com.google.gson.Gson
import com.yusufarisoy.composepagingapp.data.api.RickAndMortyServiceApi
import com.yusufarisoy.composepagingapp.data.api.RickAndMortyServiceApi.Companion.BASE_URL
import com.yusufarisoy.composepagingapp.data.repository.datasource.CharactersDataSource
import com.yusufarisoy.composepagingapp.data.repository.datasource.CharactersDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideCharactersDataSource(service: RickAndMortyServiceApi): CharactersDataSource =
        CharactersDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideRickAndMortyServiceApi(retrofit: Retrofit): RickAndMortyServiceApi =
        retrofit.create(RickAndMortyServiceApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}
