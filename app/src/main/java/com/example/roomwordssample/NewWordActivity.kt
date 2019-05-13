package com.example.roomwordssample

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_new_word.*
import android.text.TextUtils
import android.content.Intent



class NewWordActivity : AppCompatActivity() {


    companion object {
        val EXTRA_REPLY = "com.example.android.roomwordssample.REPLY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWord.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWord.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
}
