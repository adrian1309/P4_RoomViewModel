package com.example.p4_roomviewmodel.ui.detailsPerson

import androidx.lifecycle.*
import com.example.p4_roomviewmodel.R
import com.example.p4_roomviewmodel.domain.model.Person
import com.example.p4_roomviewmodel.domain.usecases.UsecaseGetPerson
import com.example.p4_roomviewmodel.domain.usecases.UsecaseUpdatePerson
import com.example.p4_roomviewmodel.domain.usecases.UsecaseValidatedPerson
import com.example.p4_roomviewmodel.ui.recycler.RecyclerEvent
import com.example.p4_roomviewmodel.utils.StringProvider
import kotlinx.coroutines.launch

class DetailsPersonViewModel (
    private val stringProvider: StringProvider,
    private val getPersonUC: UsecaseGetPerson,
    private val updatePersonUC: UsecaseUpdatePerson,
    private val validatedPersonUC: UsecaseValidatedPerson,
        ) : ViewModel(){

        private val _uiState = MutableLiveData<DetailsPersonState>()
        val uiState: LiveData<DetailsPersonState> get() = _uiState

        private fun getPerson(id: Int) {
            viewModelScope.launch {
                try {
                    _uiState.value = DetailsPersonState(
                        person = getPersonUC.getPerson(id)
                    )
                } catch (e: Exception) {
                    _uiState.value = DetailsPersonState(
                        message = e.message
                    )
                }
            }

        }

        private fun updatePerson(pNew: Person) {
            viewModelScope.launch {

                val pLast = _uiState.value?.person
                if (pNew != pLast){
                    if (pLast != null) {
                        if(validatedPersonUC.validatedPerson(pNew)){
                            updatePersonUC.updatePerson(pNew)
                            _uiState.value = DetailsPersonState(
                                person = pNew,
                                message = stringProvider.getString(R.string.niceUpdatePerson)
                            )
                        } else {
                            _uiState.value = _uiState.value?.copy(
                                //person = pLast,
                                message =  stringProvider.getString(R.string.errorUpdatePersonErrorField)
                            )
                        }
                    }
                }else{
                    //el copy sirve para no tener q estar reponiendo todos los datos del state otra vez, porq si no lo pones, coge los valores nulos, y con el copy coge los valores que ya tenia
                    _uiState.value = _uiState.value?.copy(
                        //person = pLast,
                        message = stringProvider.getString(R.string.errorUpdatePersonChangeField)
                    )
                }

            }
        }

        private fun errorMostrado() {
            //esta funcion se llama en el observe para dejar el mensaje a null de nuevo y no se quede por defecto el ultimo mensaje
            _uiState.value = _uiState.value?.copy(message = null)

        }

        fun handleEvent(event: DetailsPersonEvent) {
            when (event) {
                DetailsPersonEvent.ErrorShown -> errorMostrado()
                is DetailsPersonEvent.GetPerson -> getPerson(event.id)
                is DetailsPersonEvent.UpdatePerson -> updatePerson(event.p)
            }
        }
}

class DetailsPersonViewModelFactory(
    private val stringProvider: StringProvider,
    private val getPersonUC: UsecaseGetPerson,
    private val updatePersonUC: UsecaseUpdatePerson,
    private val validatedPersonUC: UsecaseValidatedPerson,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsPersonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsPersonViewModel(
                stringProvider,
                getPersonUC,
                updatePersonUC,
                validatedPersonUC,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}