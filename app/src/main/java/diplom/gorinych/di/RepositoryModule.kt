package diplom.gorinych.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import diplom.gorinych.data.repository.HouseRepositoryImpl
import diplom.gorinych.domain.repository.HouseRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(
        repository: HouseRepositoryImpl
    ): HouseRepository


}