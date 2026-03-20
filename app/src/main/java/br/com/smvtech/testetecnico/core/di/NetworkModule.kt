package br.com.smvtech.testetecnico.core.di

import br.com.smvtech.testetecnico.core.utils.API_URL
import br.com.smvtech.testetecnico.data.remote.api.AppService
import br.com.smvtech.testetecnico.data.repository.AppRepositoryImpl
import br.com.smvtech.testetecnico.domain.repository.AppRepository
import br.com.smvtech.testetecnico.domain.usecase.AppUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiPokemonsService(retrofit: Retrofit): AppService {
        return retrofit.create(AppService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: AppService): AppRepository {
        return AppRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: AppRepository): AppUseCase {
        return AppUseCase(repository)
    }


}