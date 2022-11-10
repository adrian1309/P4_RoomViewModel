package com.example.p4_roomviewmodel.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.p4_roomviewmodel.data.common.ConstantsData
import com.example.p4_roomviewmodel.data.database.dao.PersonDAO
import com.example.p4_roomviewmodel.data.database.modelEntity.PersonEntity

@Database(
    entities = [PersonEntity::class],
    version = 1
)
abstract class RoomPersonDB : RoomDatabase(){

    abstract fun personDao(): PersonDAO

    companion object {
        @Volatile
        private var INSTANCE: RoomPersonDB? = null

        fun getDatabase(context: Context): RoomPersonDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomPersonDB::class.java,
                    ConstantsData.PERSON_DATABASE.name
                )
                    .createFromAsset("databases/person_database.db")
                    .fallbackToDestructiveMigrationFrom()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}