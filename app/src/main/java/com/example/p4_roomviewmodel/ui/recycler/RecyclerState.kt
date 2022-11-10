package com.example.p4_roomviewmodel.ui.recycler

import com.example.p4_roomviewmodel.domain.model.Person

data class RecyclerState (
    val person: Person = Person(-1, "null","null",-1),
    val listPerson: List<Person> = ArrayList(),
    val message: String? = null,
    //el showList es para que al iniciar el activity, le des al boton y asi haya un cambio de estado,
    //y te puedo mostar la lista
)