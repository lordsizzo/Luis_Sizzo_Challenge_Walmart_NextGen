package luis.sizzo.luis_sizzo_challenge_walmart_nextgen.model.remote

import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.common.*
import retrofit2.*
import retrofit2.http.*

interface RemoteAPIConnection{

    @GET(END_POINT_COUNTRIES)
    suspend fun getCategories(
    ): Response<List<Coutries>>

}