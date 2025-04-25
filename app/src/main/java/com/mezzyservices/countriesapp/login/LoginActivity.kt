package com.mezzyservices.countriesapp.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mezzyservices.countriesapp.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {

    lateinit var view: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(view.root)
        setSupportActionBar(view.myToolbar)
    }

}