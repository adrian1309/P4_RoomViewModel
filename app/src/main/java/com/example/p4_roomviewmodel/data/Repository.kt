package com.example.p4_roomviewmodel.data

import com.example.p4_roomviewmodel.data.database.dao.PersonDAO
import com.example.p4_roomviewmodel.data.database.modelEntity.PersonEntity
import com.example.p4_roomviewmodel.domain.model.Person

class Repository (private val personDao : PersonDAO) {

    suspend fun getAllPerson() = personDao.getAllPersonEntity()

    suspend fun getPerson(id: Int) = personDao.getPersonEntity(id)

    suspend fun deletePerson(p: PersonEntity) = personDao.deletePersonEntity(p)

    suspend fun addPerson(p: PersonEntity) = personDao.addPersonEntity(p)

    suspend fun updatePerson(p: PersonEntity) = personDao.updatePersonEntity(p)

}