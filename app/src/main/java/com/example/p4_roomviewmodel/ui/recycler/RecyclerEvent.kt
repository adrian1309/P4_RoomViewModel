package com.example.p4_roomviewmodel.ui.recycler

import com.example.p4_roomviewmodel.domain.model.Person

sealed class RecyclerEvent {

    object GetAllPersons : RecyclerEvent()
    class DeletePerson(val person: Person) : RecyclerEvent()
    class AddPerson(val person: Person) : RecyclerEvent()
    object ShowList : RecyclerEvent()
    object ErrorShown : RecyclerEvent()

}
