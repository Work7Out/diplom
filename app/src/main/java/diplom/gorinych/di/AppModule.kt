package diplom.gorinych.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import diplom.gorinych.data.db.HouseBotDatabase
import diplom.gorinych.data.remote.DyplomaApi
import diplom.gorinych.domain.utils.BASE_URL
import diplom.gorinych.domain.utils.DB
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Properties
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
        )
            .createFromAsset("database.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideApiAnalytic(): DyplomaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DyplomaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMail(): Properties {
        val props = Properties()
        props["mail.smtp.host"] = "smtp.mail.ru"
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.port"] = "465"
        return props
    }
}