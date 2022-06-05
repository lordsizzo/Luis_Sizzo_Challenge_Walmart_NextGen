package luis.sizzo.luis_sizzo_challenge_walmart_nextgen.di

import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.common.BASE_URL
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.model.remote.RemoteAPIConnection
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideApiService(): RemoteAPIConnection =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RemoteAPIConnection::class.java)

    @Provides
    fun provideRepositoryLayer(service: RemoteAPIConnection): Repository =
        RepositoryImpl(service)

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideCoroutineScope(dispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(dispatcher)
}