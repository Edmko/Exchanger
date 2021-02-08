package test.privat.exchanger.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import test.privat.exchanger.domain.interactor.GetCurrencyExchangeRatesByDateUseCase
import test.privat.exchanger.domain.repository.PrivatMainRepository

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun getCurrencyExchangeRatesByDateUseCase(privatMainRepository: PrivatMainRepository) = GetCurrencyExchangeRatesByDateUseCase(privatMainRepository)
}