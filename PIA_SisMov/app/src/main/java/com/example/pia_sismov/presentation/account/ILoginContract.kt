package com.example.pia_sismov.presentation.account

import com.example.pia_sismov.domain.entities.User
import com.example.pia_sismov.presentation.shared.IBasePresenter
import com.example.pia_sismov.presentation.shared.IBaseViewWithProgress

interface ILoginContract {
    interface IView: IBaseViewWithProgress {
        fun signIn()
        fun navigateToMain()
        fun navigateToRegister()
        //fun setup()
        fun refreshUserLogStatus(isLoggedIn:Boolean)
        fun refreshUserData(loggedUser: User)
    }

    interface IPresenter: IBasePresenter<IView> {
        fun signInUserWithEmailAndPassword(email:String, password:String)
        fun checkEmptyFields(email:String, password:String): Boolean
        fun refreshUserLogStatus()
        //fun setup()
        fun getLoggedUserData()
    }
}