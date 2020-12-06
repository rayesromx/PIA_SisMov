package com.example.pia_sismov.presentation.account

import com.example.pia_sismov.presentation.account.model.UserRegisterData
import com.example.pia_sismov.presentation.shared.IBasePresenter
import com.example.pia_sismov.presentation.shared.IBaseViewWithProgress

interface IRegisterContract {
    interface IView: IBaseViewWithProgress {
        fun navigateToMain()
        fun signUp()
    }
    interface IPresenter: IBasePresenter<IView> {
        fun isUserDataValidForRegistration(data: UserRegisterData) : Boolean
        fun register(user: UserRegisterData)
    }
}