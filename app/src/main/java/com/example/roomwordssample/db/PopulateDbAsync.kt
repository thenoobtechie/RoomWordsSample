package com.example.roomwordssample.db

import android.os.AsyncTask
import com.example.roomwordssample.dao.WordsDao


class PopulateDbAsync(wordRoomDatabase: WordRoomDatabase?) : AsyncTask<Unit, Unit, Unit>() {

    var wordDao : WordsDao? = null
    var words = arrayOf("dolphin", "crocodile", "cobra")

    init {
        wordDao = wordRoomDatabase?.wordsDao()
    }
    override fun doInBackground(vararg params: Unit?) {

        wordDao?.deleteAll()

        for (currentWord in words){
            wordDao?.insert(Word(currentWord))
        }
    }
}