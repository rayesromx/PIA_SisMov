package com.example.pia_sismov.domain.interactors.login

import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.ILogOutUseCase
import com.google.firebase.auth.FirebaseAuth

class LogOut : ILogOutUseCase {
    override fun execute(listener: IBaseUseCaseCallBack<Boolean>) {
        FirebaseAuth.getInstance().signOut()
    }
}