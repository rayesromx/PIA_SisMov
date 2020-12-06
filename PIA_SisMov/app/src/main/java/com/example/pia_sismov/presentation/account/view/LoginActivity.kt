package com.example.pia_sismov.presentation.account.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.pia_sismov.R
import com.example.pia_sismov.presentation.account.ILoginContract
import com.example.pia_sismov.presentation.account.presenter.LoginPresenter
import com.example.pia_sismov.presentation.main.view.MainActivity
import com.example.pia_sismov.presentation.shared.IBasePresenter
import fcfm.lmad.poi.ChatPoi.presentation.shared.view.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<ILoginContract.IView, LoginPresenter>(),ILoginContract.IView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_login.setOnClickListener{login()}
        btn_register.setOnClickListener {
            navigateToRegister()
        }
    }

    fun login(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun getLayout() = R.layout.activity_login

    override fun instantiatePresenter() = LoginPresenter()
    override fun signIn() {
        TODO("Not yet implemented")
    }

    override fun navigateToMain() {
        TODO("Not yet implemented")
    }

    override fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun showProgressBar() {
        pbarLogin.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        pbarLogin.visibility = View.GONE
    }
}