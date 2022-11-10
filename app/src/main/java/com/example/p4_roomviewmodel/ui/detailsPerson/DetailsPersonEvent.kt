package com.example.p4_roomviewmodel.ui.detailsPerson

import com.example.p4_roomviewmodel.domain.model.Person

sealed class DetailsPersonEvent {

    class GetPerson(val id: Int): DetailsPersonEvent()
    class UpdatePerson(val p: Person): DetailsPersonEvent()
    object ErrorShown : DetailsPersonEvent()

}
