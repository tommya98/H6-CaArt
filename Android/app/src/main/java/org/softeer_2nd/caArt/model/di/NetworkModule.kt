package org.softeer_2nd.caArt.model.di

import android.content.Context
import coil.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.softeer_2nd.caArt.model.network.OptionApiService
import org.softeer_2nd.caArt.model.network.RetrofitClient
import org.softeer_2nd.caArt.model.network.TrimApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return RetrofitClient.getRetrofit()
    }

    @Singleton
    @Provides
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .build()
    }

    @Provides
    fun provideTrimApiService(retrofit: Retrofit): TrimApiService {
        return retrofit.create(TrimApiService::class.java)
    }

    @Provides
    fun provideOptionApiService(retrofit: Retrofit):OptionApiService{
        return retrofit.create(OptionApiService::class.java)
    }
}