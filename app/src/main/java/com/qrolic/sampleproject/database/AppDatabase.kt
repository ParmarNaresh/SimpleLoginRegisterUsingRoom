package com.qrolic.sampleproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qrolic.sampleproject.model.User
import com.qrolic.sampleproject.utility.AppConstants

@Database(entities = arrayOf(User::class), version = 2)
abstract class AppDatabase: RoomDatabase()  {
    abstract fun userDao(): UserDao
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    AppConstants.App_DB_Name
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}