package com.example.p4_roomviewmodel.domain.usecases

import com.example.p4_roomviewmodel.data.Repository
import com.example.p4_roomviewmodel.domain.model.Person

class UsecaseGetPerson {

    fun getPerson(positionPerson: Int): Person = Repository.getPerson(positionPerson)
}