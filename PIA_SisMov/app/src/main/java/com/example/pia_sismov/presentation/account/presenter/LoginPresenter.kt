package com.example.pia_sismov.presentation.account.presenter

import com.example.pia_sismov.CustomSessionState
import com.example.pia_sismov.domain.entities.User
import com.example.pia_sismov.domain.interactors.*
import com.example.pia_sismov.presentation.account.ILoginContract
import com.example.pia_sismov.presentation.account.model.LoginData
import com.example.pia_sismov.presentation.shared.presenter.BasePresenter

class LoginPresenter(
    private val logIn: ILogInUseCase,
    private val checkLoggedIn: ICheckLoggedInUseCase,
    private var getLoggedUser: IGetLoggedUserUseCase
): BasePresenter<ILoginContract.IView>(), ILoginContract.IPresenter{
    override fun signInUserWithEmailAndPassword(email: String, password: String) {
        view?.showProgressBar()
        val loginData = LoginData(email,password)
        logIn.execute(loginData,object: IBaseUseCaseCallBack<Boolean> {
            override fun onSuccess(data: Boolean?) {
                if(isViewAttached()){
                    view?.hideProgressBar()
                    if(data!!)
                        view?.navigateToMain()
                    else
                        view?.showError("Error al tratar de ingresar")
                }
            }

            override fun onError(error: String) {
                if(isViewAttached()){
                    view?.hideProgressBar()
                    view?.showError(error)
                }
            }
        } )
    }

    override fun checkEmptyFields(email: String, password: String) = email.isEmpty() || password.isEmpty()


    override fun refreshUserLogStatus() {
        checkLoggedIn.execute(object:IBaseUseCaseCallBack<Boolean>{
            override fun onSuccess(data: Boolean?) {
                // view!!.refreshUserLogStatus(data!!)
                if(!isViewAttached()) return
                if(data!!) {
                    getLoggedUserData()
                }
            }
            override fun onError(error: String) {
                if(!isViewAttached()) return
                view!!.showError(error)
            }
        })
    }

    override fun getLoggedUserData() {
        getLoggedUser.execute(object: IBaseUseCaseCallBack<User>{
            override fun onSuccess(data: User?) {
                if(!isViewAttached())  return
                if(data == null){
                    view!!.showError("No existe informacion del usuario")
                    return
                }

                CustomSessionState.currentUser = data!!
                /*val updateUser = UpdateUser(UserRepository())
                CustomSessionState.loggedUser.status = "online"
                updateUser.execute(CustomSessionState.loggedUser, object: IBaseUseCaseCallBack<User>{
                    override fun onSuccess(data: User?) {
                    }
                    override fun onError(error: String) {
                        view!!.showError(error)
                    }
                })*/
                view!!.refreshUserData(data)
            }

            override fun onError(error: String) {
                if(!isViewAttached())  return
                view!!.showError(error)
            }

        })
    }

}