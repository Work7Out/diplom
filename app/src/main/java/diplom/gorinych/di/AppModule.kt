package diplom.gorinych.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import diplom.gorinych.data.db.HouseBotDatabase
import diplom.gorinych.domain.utils.DB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): HouseBotDatabase {
        return Room.databaseBuilder(
            app,
            HouseBotDatabase::class.java,
            DB
        ).build()
    }
}