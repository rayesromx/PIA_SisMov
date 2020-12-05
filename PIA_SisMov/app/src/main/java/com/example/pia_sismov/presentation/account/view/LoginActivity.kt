package com.example.pia_sismov.presentation.account.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pia_sismov.R
import com.example.pia_sismov.presentation.main.view.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener{login()}
    }

    fun login(){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}