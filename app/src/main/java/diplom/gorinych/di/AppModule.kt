package diplom.gorinych.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import diplom.gorinych.data.remote.HouseBoatApi
import diplom.gorinych.domain.utils.BASE_URL
import java.util.Properties
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiAnalytic(): HouseBoatApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HouseBoatApi::class.java)
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