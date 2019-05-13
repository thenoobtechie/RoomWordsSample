package com.example.roomwordssample.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.roomwordssample.db.Word
import com.example.roomwordssample.repository.WordRepository

class WordViewModel(application: Application) : AndroidViewModel(application) {

    var repository : WordRepository? = null
    var allWordsLiveData : LiveData<List<Word>>? = null

    init {
        repository = WordRepository(application)
        allWordsLiveData = repository?.allWords
    }

    fun getAllWords() : LiveData<List<Word>>? {

        return allWordsLiveData
    }

    fun insert(word: Word) {
        repository?.insert(word)
    }
}