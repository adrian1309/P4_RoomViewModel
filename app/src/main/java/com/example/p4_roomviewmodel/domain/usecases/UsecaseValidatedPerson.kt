package com.example.p4_roomviewmodel.domain.usecases

import com.example.p4_roomviewmodel.domain.model.Person

class UsecaseValidatedPerson {

    fun validatedPerson(person: Person) : Boolean{
        var result = false
        if (!person.name.isNullOrBlank() && !person.password.isNullOrBlank() && !person.phone.toString().isBlank() && person.phone.toString().length == 9){
            result = true
        }
        return result
    }
}