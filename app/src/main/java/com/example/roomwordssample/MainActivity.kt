package com.example.roomwordssample

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.roomwordssample.NewWordActivity.Companion.EXTRA_REPLY
import com.example.roomwordssample.adapter.WordListAdapter
import com.example.roomwordssample.db.Word
import com.example.roomwordssample.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserState
import com.amazonaws.mobile.client.UserStateDetails
import com.amazonaws.mobile.client.SignInUIOptions





class MainActivity : AppCompatActivity() {


    private val NEW_WORD_ACTIVITY_REQUEST_CODE: Int = 1001;
    var wordViewModel : WordViewModel? = null
    private val TAG = MainActivity::class.java.getSimpleName()
    var wordListAdapter :WordListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordListAdapter = WordListAdapter(applicationContext)
        fab.setOnClickListener {
            startActivityForResult(Intent(this, NewWordActivity::class.java), NEW_WORD_ACTIVITY_REQUEST_CODE)
        }

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        recyclerview.layoutManager = LinearLayoutManager(applicationContext, VERTICAL, false)
        recyclerview.adapter = wordListAdapter

        wordViewModel?.allWordsLiveData?.observe(this, Observer {
            wordListAdapter?.setWords(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val word = Word(data?.getStringExtra(EXTRA_REPLY)!!)
            wordViewModel?.insert(word)
        }
        else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show();
        }
    }
}
