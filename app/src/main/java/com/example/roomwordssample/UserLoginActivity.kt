package com.example.roomwordssample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.amazonaws.mobile.client.*
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.example.roomwordssample.singup.SignUpActivity
import type.CreateTodoInput
import java.util.*

class UserLoginActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.getSimpleName()

    var awsAppSyncClient : AWSAppSyncClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        /*awsAppSyncClient = AWSAppSyncClient.builder()
            .context(applicationContext)
            .awsConfiguration(AWSConfiguration(applicationContext)).build()*/

        CreateTodoInput.builder().id(UUID.randomUUID().toString()).name("XYZ").description("asdf").build();

        //AWS Auth
        AWSMobileClient.getInstance().initialize(applicationContext, object : Callback<UserStateDetails> {

            override fun onResult(userStateDetails: UserStateDetails) {
                Log.i(TAG, userStateDetails.userState.toString())
                when (userStateDetails.userState) {
                    UserState.SIGNED_IN -> {
                        val i = Intent(applicationContext, MainActivity::class.java)
                        startActivity(i)
                    }
                    UserState.SIGNED_OUT -> showSignIn()
                    else -> {
                        AWSMobileClient.getInstance().signOut()
                        showSignIn()
                    }
                }
            }

            override fun onError(e: Exception) {
                Log.e(TAG, e.toString())
            }
        })
    }

    private fun showSignIn() {
        try {
            AWSMobileClient.getInstance().showSignIn(
                this,
                SignInUIOptions.builder().nextActivity(UserLoginActivity::class.java).build()
            )
        } catch (e: Error) {
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            Log.e(TAG, e.toString())
        }

    }

    fun startSignUp(view : View){

        startActivity(Intent(applicationContext, SignUpActivity::class.java))
    }
}
