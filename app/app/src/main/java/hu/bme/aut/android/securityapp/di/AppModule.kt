package hu.bme.aut.android.securityapp.di

import android.app.Application
import android.os.Build
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.securityapp.constants.Constants
import hu.bme.aut.android.securityapp.data.remote.JobApi
import hu.bme.aut.android.securityapp.data.remote.LoginApi
import hu.bme.aut.android.securityapp.data.remote.RegisterApi
import hu.bme.aut.android.securityapp.data.repository.JobRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.LoginRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.RegisterRepositoryImpl
import hu.bme.aut.android.securityapp.domain.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.repository.LoginRepository
import hu.bme.aut.android.securityapp.domain.repository.RegisterRepository
import hu.bme.aut.android.securityapp.network.HeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private fun checkBuildConfig(): Boolean {
        var isEmulator = (Build.MANUFACTURER.contains("Genymotion")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.toLowerCase().contains("droid4x")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.HARDWARE == "goldfish"
                || Build.HARDWARE == "vbox86"
                || Build.HARDWARE.toLowerCase().contains("nox")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.PRODUCT == "sdk"
                || Build.PRODUCT == "google_sdk"
                || Build.PRODUCT == "sdk_x86"
                || Build.PRODUCT == "vbox86p"
                || Build.PRODUCT.toLowerCase().contains("nox")
                || Build.BOARD.toLowerCase().contains("nox")
                || (Build.BRAND.startsWith("generic") &&    Build.DEVICE.startsWith("generic")))
        return isEmulator
    }

    private fun getUrl(): String{
        return Constants.SERVER_ADDRESS

        return if(checkBuildConfig())
            Constants.SERVER_ADDRESS
        else
            Constants.SERVER_ADDRESS_NOT_EMULATOR
    }

    // API-s
    @Provides
    @Singleton
    fun provideLoginApi(): LoginApi {
        return Retrofit.Builder()
            .baseUrl(getUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRegisterApi(): RegisterApi {
        return Retrofit.Builder()
            .baseUrl(getUrl())
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
            .baseUrl(getUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JobApi::class.java)
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
    ): RegisterRepository{
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
}