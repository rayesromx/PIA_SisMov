package com.example.pia_sismov.domain.interactors.login

import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.ILogInUseCase
import com.example.pia_sismov.presentation.account.model.LoginData
import com.google.firebase.auth.FirebaseAuth

class LogIn: ILogInUseCase {
    override fun execute(input: LoginData, listener: IBaseUseCaseCallBack<Boolean>) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(input.email,input.password)
            .addOnCompleteListener{
                if(it.isSuccessful) {

                    listener.onSuccess(true)
                }
                else {
                    listener.onSuccess(false)
                    listener.onError(it.exception?.message!!)
                }
            }
    }
}