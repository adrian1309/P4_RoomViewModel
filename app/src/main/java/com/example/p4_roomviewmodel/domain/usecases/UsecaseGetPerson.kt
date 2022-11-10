package com.example.p4_roomviewmodel.domain.usecases

import com.example.p4_roomviewmodel.data.Repository
import com.example.p4_roomviewmodel.data.database.dao.toPerson
import com.example.p4_roomviewmodel.domain.model.Person

class UsecaseGetPerson (val repository: Repository) {

    suspend fun getPerson(positionPerson: Int): Person = repository.getPerson(positionPerson).toPerson()
}