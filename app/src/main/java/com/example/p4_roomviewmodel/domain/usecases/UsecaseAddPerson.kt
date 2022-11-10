package com.example.p4_roomviewmodel.domain.usecases

import com.example.p4_roomviewmodel.data.Repository
import com.example.p4_roomviewmodel.data.database.dao.toPersonEntity
import com.example.p4_roomviewmodel.domain.model.Person

class UsecaseAddPerson (val repository: Repository) {

    suspend fun addPerson(person: Person) {
        repository.addPerson(person.toPersonEntity())
    }

}