package hu.bme.aut.android.securityapp.data.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.securityapp.data.remote.DashboardApi
import hu.bme.aut.android.securityapp.data.remote.JobApi
import hu.bme.aut.android.securityapp.data.remote.LoginApi
import hu.bme.aut.android.securityapp.data.remote.PersonApi
import hu.bme.aut.android.securityapp.data.remote.RegisterApi
import hu.bme.aut.android.securityapp.data.remote.RoleApi
import hu.bme.aut.android.securityapp.data.remote.ShiftApi
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
import hu.bme.aut.android.securityapp.data.repository.ShiftRepository
import hu.bme.aut.android.securityapp.data.repository.ShiftRepositoryImpl
import hu.bme.aut.android.securityapp.data.repository.WageRepository
import hu.bme.aut.android.securityapp.data.repository.WageRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

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

    @Provides
    @Singleton
    fun provideShiftRepository(
        api: ShiftApi,
        app: Application
    ): ShiftRepository {
        return ShiftRepositoryImpl(api, app)
    }
}