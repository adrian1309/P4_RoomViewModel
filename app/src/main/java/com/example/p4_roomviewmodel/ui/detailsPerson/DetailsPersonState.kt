package com.example.p4_roomviewmodel.ui.detailsPerson

import com.example.p4_roomviewmodel.domain.model.Person

data class DetailsPersonState (
    val person: Person = Person(-1,null, null, -1),
    val message: String? = null
        )