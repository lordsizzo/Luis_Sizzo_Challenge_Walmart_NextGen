package luis.sizzo.luis_sizzo_challenge_walmart_nextgen.view_model

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import luis.sizzo.luis_sizzo_challenge_walmart_nextgen.model.*
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: Repository,
    private val coroutineScope: CoroutineScope
): ViewModel() {

    private val _countriesResponse = MutableLiveData<UI_State>()
    val getCoutriesResponse: MutableLiveData<UI_State>
        get() = _countriesResponse

    private val _stateView = MutableLiveData<Boolean>()
    val stateView: MutableLiveData<Boolean>
        get() = _stateView

    fun getCountries(){
        coroutineScope.launch {
            repository.getCountries().collect {
                _countriesResponse.postValue(it)
            }
        }
    }
    fun getStateView(state: Boolean){
        _stateView.value = state
    }
}