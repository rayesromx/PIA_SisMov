package com.example.pia_sismov.domain.interactors.login

import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.ICheckLoggedInUseCase
import com.google.firebase.auth.FirebaseAuth

class CheckLoggedIn : ICheckLoggedInUseCase {
    override fun execute(listener: IBaseUseCaseCallBack<Boolean>) {
        listener.onSuccess(FirebaseAuth.getInstance().currentUser != null)
    }
}