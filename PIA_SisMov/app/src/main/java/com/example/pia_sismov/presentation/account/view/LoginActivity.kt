package com.example.pia_sismov.presentation.account.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import com.example.pia_sismov.CustomSessionState
import com.example.pia_sismov.DataBaseHandler
import com.example.pia_sismov.R
import com.example.pia_sismov.domain.entities.User
import com.example.pia_sismov.domain.interactors.login.CheckLoggedIn
import com.example.pia_sismov.domain.interactors.login.LogIn
import com.example.pia_sismov.domain.interactors.user.GetLoggedUser
import com.example.pia_sismov.presentation.account.ILoginContract
import com.example.pia_sismov.presentation.account.model.LoginData
import com.example.pia_sismov.presentation.account.presenter.LoginPresenter
import com.example.pia_sismov.presentation.main.view.MainActivity
import com.example.pia_sismov.repos.UserRepository
import fcfm.lmad.poi.ChatPoi.presentation.shared.view.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<ILoginContract.IView, LoginPresenter>(),ILoginContract.IView {

    lateinit var db: DataBaseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DataBaseHandler(this)
        btn_login.setOnClickListener{ signIn()}
        btn_register.setOnClickListener {
            navigateToRegister()
        }

        if(this.isConnectedToNetwork()){
            presenter.refreshUserLogStatus()
            CustomSessionState.hayInternet = true
        }
        else{
            CustomSessionState.hayInternet = false
        }
    }

    override fun signIn() {
        val email = etxt_email.text.toString().trim()
        val password = etxt_password.text.toString().trim()
        if(CustomSessionState.hayInternet) {
            if (presenter.checkEmptyFields(email, password))
                toast(this, "Revisa el email o contrasena")
            else
                presenter.signInUserWithEmailAndPassword(email, password)
        }
        else{
            val loginData = db.readLoginData()
            var userExists = false
            for(ld in loginData){
                if(ld.email.toLowerCase() == email.toLowerCase() /*&& ld.password == password*/){
                    CustomSessionState.currentUser = db.getUserByLoginData(ld)
                    userExists = true
                    break
                }
            }
            if(userExists)
                navigateToMain()
            else
                toast(this, "No hay usuarios registrados. Registrate!")
        }
    }

    override fun refreshUserData(loggedUser: User) {
        val email = etxt_email.text.toString().trim()
        val password = etxt_password.text.toString().trim()
        val loginData = LoginData(email, password)

        if(!db.existLoginData(loggedUser))
            db.insertlOGINData(loginData, loggedUser)
        else
            db.udpateLogin(loginData, loggedUser)
        CustomSessionState.currentUser = loggedUser
        navigateToMain()
    }

    override fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
    fun Context.isConnectedToNetwork(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting() ?: false
    }
    override fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
    override fun refreshUserLogStatus(isLoggedIn: Boolean) { if(isLoggedIn)  navigateToMain() }
    override fun showProgressBar() {
        pbarLogin.visibility = View.VISIBLE
    }
    override fun hideProgressBar() {
        pbarLogin.visibility = View.GONE
    }
    override fun getLayout() = R.layout.activity_login
    override fun instantiatePresenter() = LoginPresenter(
        LogIn(),
        CheckLoggedIn(),
        GetLoggedUser(UserRepository())
    )
}