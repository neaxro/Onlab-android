package hu.bme.aut.android.securityapp.data.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.securityapp.constants.Constants
import hu.bme.aut.android.securityapp.data.remote.DashboardApi
import hu.bme.aut.android.securityapp.data.remote.JobApi
import hu.bme.aut.android.securityapp.data.remote.LoginApi
import hu.bme.aut.android.securityapp.data.remote.PersonApi
import hu.bme.aut.android.securityapp.data.remote.RegisterApi
import hu.bme.aut.android.securityapp.data.remote.RoleApi
import hu.bme.aut.android.securityapp.data.remote.WageApi
import hu.bme.aut.android.securityapp.data.repository.DashboardRepository
import hu.bme.aut.android.securityapp.data.repository.DashboardRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.JobRepository
import hu.bme.aut.android.securityapp.data.repository.JobRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.LoginRepository
import hu.bme.aut.android.securityapp.data.repository.LoginRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.PersonRepository
import hu.bme.aut.android.securityapp.data.repository.PersonRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.RegisterRepository
import hu.bme.aut.android.securityapp.data.repository.RegisterRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.RoleRepository
import hu.bme.aut.android.securityapp.data.repository.RoleRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.WageRepository
import hu.bme.aut.android.securityapp.data.repository.WageRepositoryImpl
import hu.bme.aut.android.securityapp.network.HeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    // API-s
    @Provides
    @Singleton
    fun provideLoginApi(): LoginApi {
        return Retrofit.Builder()
            .baseUrl(Constants.SERVER_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRegisterApi(): RegisterApi {
        return Retrofit.Builder()
            .baseUrl(Constants.SERVER_ADDRESS)
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
            .baseUrl(Constants.SERVER_ADDRESS)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JobApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDashboardApi(): DashboardApi {
        return Retrofit.Builder()
            .baseUrl(Constants.SERVER_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DashboardApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWageApi(): WageApi {
        return Retrofit.Builder()
            .baseUrl(Constants.SERVER_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WageApi::class.java)
    }

    @Provides
    @Singleton
    fun providePersonApi(): PersonApi {
        return Retrofit.Builder()
            .baseUrl(Constants.SERVER_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PersonApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRoleApi(): RoleApi {
        return Retrofit.Builder()
            .baseUrl(Constants.SERVER_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RoleApi::class.java)
    }

    // REPOSITORY-s
    @Provides
    @Singleton
    fun provideLoginRepository(
        api: LoginApi,
        app: Application
    ): LoginRepository {
        return LoginRepositoryImpl(api, app)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(
        api: RegisterApi,
        app: Application
    ): RegisterRepository {
        return RegisterRepositoryImpl(api, app)
    }

    @Provides
    @Singleton
    fun provideJobRepository(
        api: JobApi,
        app: Application
    ): JobRepository {
        return JobRepositoryImpl(api, app)
    }

    @Provides
    @Singleton
    fun provideDashboardRepository(
        api: DashboardApi,
        app: Application
    ): DashboardRepository {
        return DashboardRepositoryImpl(api, app)
    }

    @Provides
    @Singleton
    fun provideWageRepository(
        api: WageApi,
        app: Application
    ): WageRepository {
        return WageRepositoryImpl(api, app)
    }

    @Provides
    @Singleton
    fun providePersonRepository(
        api: PersonApi,
        app: Application
    ): PersonRepository {
        return PersonRepositoryImpl(api, app)
    }

    @Provides
    @Singleton
    fun provideRoleRepository(
        api: RoleApi,
        app: Application
    ): RoleRepository {
        return RoleRepositoryImpl(api, app)
    }
}