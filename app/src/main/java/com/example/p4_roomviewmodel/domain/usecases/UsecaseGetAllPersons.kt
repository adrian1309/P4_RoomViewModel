package com.example.p4_roomviewmodel.domain.usecases

import com.example.p4_roomviewmodel.data.Repository
import com.example.p4_roomviewmodel.data.database.dao.toPerson
import com.example.p4_roomviewmodel.domain.model.Person

class UsecaseGetAllPersons (val repository: Repository) {

    suspend fun getAllPersons(): List<Person> = repository.getAllPerson().map { pEntity -> pEntity.toPerson() }
}