package com.example.roomwordssample.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.room.*
import android.content.Context
import com.example.roomwordssample.dao.WordsDao

@Database(entities = [Word::class], version = 1, exportSchema = false)
//Setting exportSchema to false will ensure to never migrate db to newer versions,
// it'll simply be recreated if app is updated to newer database versions
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordsDao() : WordsDao?

    companion object {

        internal var INSTANCE : WordRoomDatabase? = null
        fun getDatabase(context: Context) : WordRoomDatabase? {
            if (INSTANCE == null) {

                synchronized(WordRoomDatabase::class.java){

                    if (INSTANCE == null)
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            WordRoomDatabase::class.java, "word_database")
                            //TODO deleting whole database and recreating again if previos versions are not found
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build()
                }
            }

            return INSTANCE
        }

        var sRoomDatabaseCallback : Callback  = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE).execute()
            }
        }
    }

}