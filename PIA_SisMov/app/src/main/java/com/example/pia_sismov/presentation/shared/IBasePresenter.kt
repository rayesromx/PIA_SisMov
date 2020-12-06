package com.example.pia_sismov.presentation.shared


interface IBasePresenter<T> {
    fun attachView(view: T)
    fun detachView()
    fun isViewAttached():Boolean
    fun showError(error:String)
}
