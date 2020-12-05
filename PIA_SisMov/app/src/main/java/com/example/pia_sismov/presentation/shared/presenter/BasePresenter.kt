package com.example.pia_sismov.presentation.shared.presenter

import com.example.pia_sismov.presentation.shared.IBasePresenter

open class BasePresenter<T>(): IBasePresenter<T> {
    var view: T? = null
    override fun attachView(view: T) {
        this.view = view
    }
    override fun detachView() {
        view = null
    }
    override fun isViewAttached(): Boolean = view != null
}