package br.com.smvtech.testetecnico.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import br.com.smvtech.testetecnico.core.utils.API_URL
import br.com.smvtech.testetecnico.core.utils.DATABASE_NAME
import br.com.smvtech.testetecnico.data.local.database.AppDatabase
import br.com.smvtech.testetecnico.data.local.database.dao.WorkshopDao
import br.com.smvtech.testetecnico.data.local.sharedpreferences.SharedPrefsService
import br.com.smvtech.testetecnico.data.local.sharedpreferences.SharedPrefsServiceImpl
import br.com.smvtech.testetecnico.data.location.LocationProviderImpl
import br.com.smvtech.testetecnico.data.remote.api.AppService
import br.com.smvtech.testetecnico.data.repository.AppRepositoryImpl
import br.com.smvtech.testetecnico.domain.location.LocationProvider
import br.com.smvtech.testetecnico.domain.repository.AppRepository
import br.com.smvtech.testetecnico.domain.usecase.AppUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideApiService(retrofit: Retrofit): AppService {
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


    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideWorkshopDao(appDatabase: AppDatabase): WorkshopDao {
        return appDatabase.workshopDao()

    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)

    }

    @Provides
    @Singleton
    fun provideLocationProvider(
        @ApplicationContext context: Context,
        fusedLocationClient: FusedLocationProviderClient
    ): LocationProvider = LocationProviderImpl(context, fusedLocationClient)

    @Provides
    @Singleton
    fun provideSharedPrefsService(
        @ApplicationContext context: Context
    ): SharedPrefsService {
        return SharedPrefsServiceImpl(context)
    }


}