package com.example.p4_roomviewmodel.data.database.dao

import com.example.p4_roomviewmodel.data.database.modelEntity.PersonEntity
import com.example.p4_roomviewmodel.domain.model.Person

fun PersonEntity.toPerson() : Person {
    return Person(this.id, this.name, this.password,this.phone)
}

fun Person.toPersonEntity() : PersonEntity {
    return PersonEntity(this.id, this.name, this.password, this.phone)
}