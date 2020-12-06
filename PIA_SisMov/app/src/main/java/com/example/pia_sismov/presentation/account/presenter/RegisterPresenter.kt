package com.example.pia_sismov.presentation.account.presenter

import androidx.core.util.PatternsCompat
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.IRegisterUserUseCase
import com.example.pia_sismov.presentation.account.IRegisterContract
import com.example.pia_sismov.presentation.account.model.UserRegisterData
import com.example.pia_sismov.presentation.shared.presenter.BasePresenter

class RegisterPresenter(
    private val registerUser: IRegisterUserUseCase
) : BasePresenter<IRegisterContract.IView>(), IRegisterContract.IPresenter {

    override fun register(user: UserRegisterData) {
        view?.showProgressBar()
        registerUser.execute(user,object: IBaseUseCaseCallBack<Boolean> {
            override fun onSuccess(data: Boolean?) {
                view?.navigateToMain()
                view?.hideProgressBar()
            }

            override fun onError(error: String) {
                view?.showError(error)
                view?.hideProgressBar()
            }
        })
    }

    override fun isUserDataValidForRegistration(data: UserRegisterData): Boolean{
        if(data.name.isEmpty()){
            showError("El nombre esta vacio")
            return false
        }

        if(data.lastName.isEmpty()){
            showError("El apellido esta vacio")
            return false
        }

        if(data.email.isEmpty()){
            showError("El correo  esta vacio")
            return false
        }

        if(!PatternsCompat.EMAIL_ADDRESS.matcher(data.email).matches()) {
            showError("El correo no es valido")
            return false
        }

        if(data.pass1.isEmpty()){
            showError("La contrasena esta vacia")
            return false
        }

        if(data.pass2.isEmpty()){
            showError("La contrasena de confirmacion esta vacia")
            return false
        }

        if(data.pass1 != data.pass2){
            showError("Las contrasenas no coinciden")
            return false
        }

        return true
    }

}