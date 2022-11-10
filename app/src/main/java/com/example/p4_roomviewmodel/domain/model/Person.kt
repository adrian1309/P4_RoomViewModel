package com.example.p4_roomviewmodel.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(val id: Int, val name: String?, val password: String?, val phone: Int) : Parcelable
