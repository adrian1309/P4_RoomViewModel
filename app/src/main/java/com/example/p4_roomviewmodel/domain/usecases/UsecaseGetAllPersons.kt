package com.example.p4_roomviewmodel.domain.usecases

import com.example.p4_roomviewmodel.data.Repository
import com.example.p4_roomviewmodel.domain.model.Person

class UsecaseGetAllPersons {

    fun getAllPersons(): List<Person> = Repository.getPersons()
}