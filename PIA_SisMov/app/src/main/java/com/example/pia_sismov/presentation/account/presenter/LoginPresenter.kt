package com.example.pia_sismov.presentation.account.presenter

import com.example.pia_sismov.presentation.account.ILoginContract
import com.example.pia_sismov.presentation.shared.presenter.BasePresenter


class LoginPresenter(


): BasePresenter<ILoginContract.IView>(), ILoginContract.IPresenter{
    override fun signInUserWithEmailAndPassword(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun checkEmptyFields(email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

}