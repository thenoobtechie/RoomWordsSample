package com.example.roomwordssample.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.example.roomwordssample.db.Word

@Dao
interface WordsDao {

    @Insert(onConflict = REPLACE)
    fun insert(word: Word)

    @Query("DELETE FROM word_table")
    fun deleteAll()

    @Query("SELECT *  FROM word_table ORDER BY word ASC")
    fun getAllWords() : LiveData<List<Word>>
}