package com.example.pia_sismov.presentation.account.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.pia_sismov.CustomSessionState
import com.example.pia_sismov.R
import com.example.pia_sismov.domain.entities.User
import com.example.pia_sismov.domain.interactors.login.CheckLoggedIn
import com.example.pia_sismov.domain.interactors.login.GetLoggedUserData
import com.example.pia_sismov.domain.interactors.login.LogIn
import com.example.pia_sismov.domain.interactors.user.GetLoggedUser
import com.example.pia_sismov.presentation.account.ILoginContract
import com.example.pia_sismov.presentation.account.presenter.LoginPresenter
import com.example.pia_sismov.presentation.main.view.MainActivity
import com.example.pia_sismov.repos.UserRepository
import fcfm.lmad.poi.ChatPoi.presentation.shared.view.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<ILoginContract.IView, LoginPresenter>(),ILoginContract.IView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_login.setOnClickListener{ signIn()}
        btn_register.setOnClickListener {
            navigateToRegister()
        }
    }

    override fun getLayout() = R.layout.activity_login
    override fun instantiatePresenter() = LoginPresenter(
        LogIn(),
        CheckLoggedIn(),
        GetLoggedUser(UserRepository())
    )

    override fun signIn() {
        val email = etxt_email.text.toString().trim()
        val password = etxt_password.text.toString().trim()
        if(presenter.checkEmptyFields(email, password))
            toast(this,"Revisa el email o contrasena")
        else
            presenter.signInUserWithEmailAndPassword(email, password)
    }

    override fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun refreshUserLogStatus(isLoggedIn: Boolean) { if(isLoggedIn)  navigateToMain() }

    override fun refreshUserData(loggedUser: User) {
        CustomSessionState.currentUser = loggedUser
        navigateToMain()
    }

    override fun showProgressBar() {
        pbarLogin.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        pbarLogin.visibility = View.GONE
    }
}