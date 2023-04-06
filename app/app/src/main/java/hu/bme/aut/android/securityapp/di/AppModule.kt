package hu.bme.aut.android.securityapp.di

import android.app.Application
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.securityapp.constants.Constants
import hu.bme.aut.android.securityapp.data.remote.LoginApi
import hu.bme.aut.android.securityapp.data.remote.RegisterApi
import hu.bme.aut.android.securityapp.data.repository.LoginRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.RegisterRepositoryImpl
import hu.bme.aut.android.securityapp.domain.repository.LoginRepository
import hu.bme.aut.android.securityapp.domain.repository.RegisterRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun provideLoginRepository(
        api: LoginApi,
        app: Application
    ): LoginRepository {
        return LoginRepositoryImpl(api, app)
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
    fun provideRegisterRepository(
        api: RegisterApi,
        app: Application
    ): RegisterRepository{
        return RegisterRepositoryImpl(api, app)
    }
}