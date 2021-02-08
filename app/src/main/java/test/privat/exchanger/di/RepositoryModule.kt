package test.privat.exchanger.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import test.privat.exchanger.data.repository.PrivatMainRepositoryImpl
import test.privat.exchanger.domain.repository.PrivatMainRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    internal abstract fun providePrivatMainRepository(privatMainRepository: PrivatMainRepositoryImpl): PrivatMainRepository
}