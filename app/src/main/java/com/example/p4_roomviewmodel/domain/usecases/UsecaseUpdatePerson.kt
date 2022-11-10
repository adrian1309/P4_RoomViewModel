package com.example.p4_roomviewmodel.domain.usecases

import com.example.p4_roomviewmodel.data.Repository
import com.example.p4_roomviewmodel.data.database.dao.toPersonEntity
import com.example.p4_roomviewmodel.domain.model.Person

class UsecaseUpdatePerson (val repository: Repository) {

    suspend fun updatePerson(person: Person) = repository.updatePerson(person.toPersonEntity())
}