package com.example.pia_sismov.presentation.shared

interface IBaseView {
    fun showError(errorMsg:String)

}

interface IBaseViewWithProgress:IBaseView{
    fun showProgressBar()
    fun hideProgressBar()
}