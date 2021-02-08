package test.privat.exchanger.di

import androidx.viewbinding.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import test.privat.exchanger.BuildConfig.PRIVAT_BASE_URL
import test.privat.exchanger.data.remote.api.PrivatApiServiceFactory

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideApiClient() = PrivatApiServiceFactory.newInstance(PRIVAT_BASE_URL)
}