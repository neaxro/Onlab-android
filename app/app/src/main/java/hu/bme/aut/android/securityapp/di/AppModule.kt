package hu.bme.aut.android.securityapp.di

import android.app.Application
import android.os.Build
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.securityapp.constants.Constants
import hu.bme.aut.android.securityapp.data.remote.DashboardApi
import hu.bme.aut.android.securityapp.data.remote.JobApi
import hu.bme.aut.android.securityapp.data.remote.LoginApi
import hu.bme.aut.android.securityapp.data.remote.RegisterApi
import hu.bme.aut.android.securityapp.data.remote.WageApi
import hu.bme.aut.android.securityapp.data.repository.DashboardRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.JobRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.LoginRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.RegisterRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.WageRepositoryImpl
import hu.bme.aut.android.securityapp.domain.repository.DashboardRepository
import hu.bme.aut.android.securityapp.domain.repository.JobRepository
import hu.bme.aut.android.securityapp.domain.repository.LoginRepository
import hu.bme.aut.android.securityapp.domain.repository.RegisterRepository
import hu.bme.aut.android.securityapp.domain.repository.WageRepository
import hu.bme.aut.android.securityapp.network.HeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    val isProbablyRunningOnEmulator: Boolean by lazy {
        // Android SDK emulator
        return@lazy ((Build.MANUFACTURER == "Google" && Build.BRAND == "google" &&
                ((Build.FINGERPRINT.startsWith("google/sdk_gphone_")
                        && Build.FINGERPRINT.endsWith(":user/release-keys")
                        && Build.PRODUCT.startsWith("sdk_gphone_")
                        && Build.MODEL.startsWith("sdk_gphone_"))
                        //alternative
                        || (Build.FINGERPRINT.startsWith("google/sdk_gphone64_")
                        && (Build.FINGERPRINT.endsWith(":userdebug/dev-keys") || Build.FINGERPRINT.endsWith(":user/release-keys"))
                        && Build.PRODUCT.startsWith("sdk_gphone64_")
                        && Build.MODEL.startsWith("sdk_gphone64_"))))
                //
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                //bluestacks
                || "QC_Reference_Phone" == Build.BOARD && !"Xiaomi".equals(Build.MANUFACTURER, ignoreCase = true)
                //bluestacks
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.HOST.startsWith("Build")
                //MSI App Player
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.PRODUCT == "google_sdk")
    }

    private fun getUrl(): String{
        return Constants.SERVER_ADDRESS

        return if(isProbablyRunningOnEmulator)
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

    @Provides
    @Singleton
    fun provideDashboardApi(): DashboardApi {
        return Retrofit.Builder()
            .baseUrl(getUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DashboardApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWageApi(): WageApi {
        return Retrofit.Builder()
            .baseUrl(getUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WageApi::class.java)
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
}