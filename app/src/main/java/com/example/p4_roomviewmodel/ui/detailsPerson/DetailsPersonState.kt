package com.example.p4_roomviewmodel.ui.detailsPerson

import com.example.p4_roomviewmodel.domain.model.Person

data class DetailsPersonState (
    val person: Person = Person(null, null, 0),
    val message: String? = null
        )