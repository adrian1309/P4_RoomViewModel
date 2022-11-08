package com.example.p4_roomviewmodel.domain.usecases

import com.example.p4_roomviewmodel.data.Repository

class UsecaseDeletePerson {

    fun deletePerson(position: Int) =
        Repository.deletePerson(position)
}