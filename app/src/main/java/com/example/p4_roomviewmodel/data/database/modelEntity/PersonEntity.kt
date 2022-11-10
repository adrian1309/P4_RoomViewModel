package com.example.p4_roomviewmodel.data.database.modelEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personEntity_table")
data class PersonEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int =0,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "password")
    val password: String?,
    @ColumnInfo(name = "phone")
    val phone: Int,

)