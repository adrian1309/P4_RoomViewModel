package com.example.p4_roomviewmodel.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.p4_roomviewmodel.data.database.modelEntity.PersonEntity

@Dao
interface PersonDAO {

    @Query("SELECT * FROM personEntity_table")
    suspend fun getAllPersonEntity() : List<PersonEntity>

    @Query("SELECT * FROM personEntity_table WHERE id = :id")
    suspend fun getPersonEntity(id: Int) : PersonEntity

    @Delete
    suspend fun deletePersonEntity(p: PersonEntity)

    @Insert
    suspend fun addPersonEntity(p: PersonEntity)

    @Update
    suspend fun updatePersonEntity(p: PersonEntity)
}