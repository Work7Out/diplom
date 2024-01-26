package diplom.gorinych.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import diplom.gorinych.data.repository.MailRepositoryImpl
import diplom.gorinych.data.repository.RemoteRepositoryImpl
import diplom.gorinych.data.repository.SharedRepositoryImpl
import diplom.gorinych.domain.repository.MailRepository
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.repository.SharedRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


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

    @Binds
    @Singleton
    abstract fun bindSharedRepository(
        repository: SharedRepositoryImpl
    ): SharedRepository

}