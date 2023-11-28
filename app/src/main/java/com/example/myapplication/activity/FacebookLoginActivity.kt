package com.example.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityFacebookLoginBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

class FacebookLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFacebookLoginBinding
    private lateinit var callbackManager: CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_facebook_login)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_facebook_login)
        FacebookSdk.sdkInitialize(applicationContext)
        callbackManager=CallbackManager.Factory.create()
//        LoginManager.getInstance().registerCallback(callbackManager,
//            FacebookCallback<LoginResult> {}
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {

                override fun onSuccess(loginResult: LoginResult) {


                }

                override fun onCancel() {
                    Log.d("letsSee", "Facebook onCancel.")

                }

                override fun onError(error: FacebookException) {
                    Log.d("letsSee", "Facebook onError.")

                }
            })
    }

}