package luis.sizzo.luis_sizzo_challenge_walmart_nextgen.model.res

import kotlinx.coroutines.flow.*
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.model.UI_State
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.model.remote.RemoteAPIConnection
import javax.inject.Inject

interface Repository {
    fun getCountries(): Flow<UI_State>
}

class RepositoryImpl @Inject constructor(
    private val service: RemoteAPIConnection,
): Repository {

    override fun getCountries() = flow {
        emit(UI_State.LOADING)
        try {
            val respose = service.getCategories()
            if (respose.isSuccessful) {
                respose.body()?.let { result ->
                    emit(UI_State.SUCCESS(result))
                } ?: throw Exception("Error null")
            } else {
                throw Exception("Error failure")
            }
        } catch (e: Exception) {
            emit(UI_State.ERROR(e))
        }
    }
}