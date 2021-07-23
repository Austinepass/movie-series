package com.orgustine.moviesapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orgustine.moviesapp.data.MovieRemoteDataSource
import com.orgustine.moviesapp.data.MoviesRepository
import com.orgustine.moviesapp.data.remote.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://www.episodate.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: MovieRemoteDataSource) =
        MoviesRepository(remoteDataSource)
}