package com.example.p4_roomviewmodel.domain.usecases

import com.example.p4_roomviewmodel.data.Repository
import com.example.p4_roomviewmodel.data.database.dao.toPersonEntity
import com.example.p4_roomviewmodel.domain.model.Person

class UsecaseDeletePerson (val repository: Repository) {

    suspend fun deletePerson(p: Person) =
        repository.deletePerson(p.toPersonEntity())
}