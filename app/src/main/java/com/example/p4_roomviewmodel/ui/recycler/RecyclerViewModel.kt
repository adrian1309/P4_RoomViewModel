package com.example.p4_roomviewmodel.ui.recycler

import androidx.lifecycle.*
import com.example.p4_roomviewmodel.R
import com.example.p4_roomviewmodel.domain.model.Person
import com.example.p4_roomviewmodel.domain.usecases.*
import com.example.p4_roomviewmodel.utils.StringProvider
import kotlinx.coroutines.launch

class RecyclerViewModel (
    private val stringProvider: StringProvider,
    private val getAllPersonsUC: UsecaseGetAllPersons,
    private val deletePersonUC: UsecaseDeletePerson,
    private val addPersonUC: UsecaseAddPerson,
    private val validatedPersonUC: UsecaseValidatedPerson,
        ) : ViewModel() {

        private val _uiState = MutableLiveData<RecyclerState>()
        val uiState: LiveData<RecyclerState> get() = _uiState

        private fun getAllPersons() {
            viewModelScope.launch() {
                try {
                    _uiState.value = _uiState.value?.copy(
                        listPerson = getAllPersonsUC.getAllPersons()
                    )

                }
                catch (e: Exception) {
                    _uiState.value =_uiState.value?.copy(message = e.message)
                }
            }
        }

        private fun deletePerson(person: Person) {
            viewModelScope.launch() {
                try {
                    deletePersonUC.deletePerson(person)
                    _uiState.value = _uiState.value?.copy(
                        listPerson = getAllPersonsUC.getAllPersons()
                    )
                } catch (e: Exception) {
                    _uiState.value = _uiState.value?.copy(
                        message = stringProvider.getString(R.string.errorDeletePerson)
                    )
                }
            }
        }

        private fun addPerson(p : Person){
            viewModelScope.launch() {
                if (validatedPersonUC.validatedPerson(p)) {
                    try {
                        addPersonUC.addPerson(p)
                        _uiState.value = _uiState.value?.copy(
                            listPerson = getAllPersonsUC.getAllPersons(),
                            message = stringProvider.getString(R.string.niceAddedPerson)
                        )
                    } catch (e: Exception) {
                        _uiState.value = _uiState.value?.copy(
                            message = stringProvider.getString(R.string.errorAddPerson)
                        )
                    }
                } else {
                    _uiState.value = _uiState.value?.copy(
                        //person = pLast,
                        message =  stringProvider.getString(R.string.errorValidatePerson)
                    )
                }

            }
        }

        private fun showList() {
            viewModelScope.launch() {
                try {
                    _uiState.value = RecyclerState(
                        listPerson = getAllPersonsUC.getAllPersons(),
                    )
                } catch (e: Exception) {
                    _uiState.value = RecyclerState(
                        message = e.message
                    )
                }
            }

        }

        private fun errorMostrado() {
            //esta funcion se llama en el observe para dejar el mensaje a null de nuevo y no se quede por defecto el ultimo mensaje
            _uiState.value = _uiState.value?.copy(
                message = null
            )
        }

        fun handleEvent(event: RecyclerEvent) {
            when (event) {
                is RecyclerEvent.AddPerson -> addPerson(event.person)
                is RecyclerEvent.DeletePerson -> deletePerson(event.person)
                RecyclerEvent.ErrorShown -> errorMostrado()
                RecyclerEvent.GetAllPersons -> getAllPersons()
                RecyclerEvent.ShowList -> showList()
            }
        }

}

class RecyclerViewModelFactory(
    private val stringProvider: StringProvider,
    private val getAllPersonsUC: UsecaseGetAllPersons,
    private val deletePersonUC: UsecaseDeletePerson,
    private val addPersonUC: UsecaseAddPerson,
    private val validatedPersonUC: UsecaseValidatedPerson
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecyclerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecyclerViewModel(
                stringProvider,
                getAllPersonsUC,
                deletePersonUC,
                addPersonUC,
                validatedPersonUC,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}