package com.example.pia_sismov.presentation.main

import com.example.pia_sismov.presentation.shared.IBasePresenter
import com.example.pia_sismov.presentation.shared.IBaseView

interface IMainContract {
    interface IView: IBaseView {
        fun onAddNewPostClick()
        fun finishActivity()
    }

    interface IPresenter: IBasePresenter<IView> {

    }
}