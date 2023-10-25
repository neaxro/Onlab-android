package hu.bme.aut.android.securityapp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.securityapp.BuildConfig
import hu.bme.aut.android.securityapp.data.remote.DashboardApi
import hu.bme.aut.android.securityapp.data.remote.JobApi
import hu.bme.aut.android.securityapp.data.remote.LoginApi
import hu.bme.aut.android.securityapp.data.remote.PersonApi
import hu.bme.aut.android.securityapp.data.remote.RegisterApi
import hu.bme.aut.android.securityapp.data.remote.RoleApi
import hu.bme.aut.android.securityapp.data.remote.ShiftApi
import hu.bme.aut.android.securityapp.data.remote.WageApi
import hu.bme.aut.android.securityapp.network.HeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideLoginApi(): LoginApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRegisterApi(): RegisterApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegisterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideJobApi(): JobApi {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JobApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDashboardApi(): DashboardApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DashboardApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWageApi(): WageApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WageApi::class.java)
    }

    @Provides
    @Singleton
    fun providePersonApi(): PersonApi {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PersonApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRoleApi(): RoleApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RoleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideShiftApi(): ShiftApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShiftApi::class.java)
    }
}