package diplom.gorinych.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import diplom.gorinych.data.repository.HouseRepositoryImpl
import diplom.gorinych.data.repository.MailRepositoryImpl
import diplom.gorinych.data.repository.RemoteRepositoryImpl
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.repository.MailRepository
import diplom.gorinych.domain.repository.RemoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(
        repository: HouseRepositoryImpl
    ): HouseRepository

    @Binds
    @Singleton
    abstract fun bindMailRepository(
        repository: MailRepositoryImpl
    ): MailRepository

    @Binds
    @Singleton
    abstract fun bindRemoteRepository(
        repository: RemoteRepositoryImpl
    ): RemoteRepository

}