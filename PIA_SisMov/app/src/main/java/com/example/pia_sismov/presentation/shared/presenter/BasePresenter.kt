package com.example.pia_sismov.presentation.shared.presenter

import com.example.pia_sismov.presentation.shared.IBasePresenter
import com.example.pia_sismov.presentation.shared.IBaseView

open class BasePresenter<TView>(): IBasePresenter<TView> where TView: IBaseView {
    var view: TView? = null
    override fun attachView(view: TView) {
        this.view = view
    }
    override fun detachView() {
        view = null
    }
    override fun isViewAttached(): Boolean = view != null
    override fun showError(error: String) {
        if(!isViewAttached())
            throw Exception("No view attached to presenter")
        view!!.showError(error)
    }
}