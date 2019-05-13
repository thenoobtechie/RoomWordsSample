package com.example.roomwordssample.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.roomwordssample.dao.WordsDao
import com.example.roomwordssample.db.Word
import com.example.roomwordssample.db.WordRoomDatabase

class WordRepository(application: Application) {

    internal var wordsDao : WordsDao? = null
    internal var allWords : LiveData<List<Word>>? = null

    init {
        val wordRoomDatabase : WordRoomDatabase? = WordRoomDatabase.getDatabase(application)
        wordsDao = wordRoomDatabase?.wordsDao()
        allWords = wordsDao?.getAllWords()
    }

    fun getAllWords() : LiveData<List<Word>>? {
        return allWords
    }

    fun insert(word: Word) {
        InsertAsyncTask(wordsDao).execute(word)
    }

    class InsertAsyncTask(wordsDao: WordsDao?) : AsyncTask<Word, Unit, Unit>() {

        var asyncTaskDao : WordsDao? = null

        init {
            asyncTaskDao = wordsDao
        }
        override fun doInBackground(vararg params: Word?) {

            asyncTaskDao?.insert(params[0]!!)
        }

    }
}