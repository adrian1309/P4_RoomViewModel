package com.example.p4_roomviewmodel.domain.usecases

import com.example.p4_roomviewmodel.data.Repository
import com.example.p4_roomviewmodel.domain.model.Person

class UsecaseUpdatePerson {

    fun updatePerson(pLast: Person, pNew: Person) = Repository.updatePerson(pLast, pNew)
}