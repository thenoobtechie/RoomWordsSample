package com.example.roomwordssample.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "word_table")
class Word(word: String) {

    @ColumnInfo(name = "word")
    var mWord : String = ""

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sno")
    var sno : Int? = null

    init {
        mWord = word
    }
}