package com.example.pia_sismov.presentation.account.presenter

import android.net.Uri
import androidx.core.util.PatternsCompat
import com.example.pia_sismov.domain.entities.User
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.IRegisterUserUseCase
import com.example.pia_sismov.domain.interactors.posts.UploadPP
import com.example.pia_sismov.presentation.account.IRegisterContract
import com.example.pia_sismov.presentation.account.model.UserRegisterData
import com.example.pia_sismov.presentation.posts.model.DtoDocument
import com.example.pia_sismov.presentation.shared.presenter.BasePresenter
import com.example.pia_sismov.repos.IRepository
import com.example.pia_sismov.repos.UserRepository

class RegisterPresenter(
    private val registerUser: IRegisterUserUseCase,
    val uploadpp: UploadPP
) : BasePresenter<IRegisterContract.IView>(), IRegisterContract.IPresenter {

    override fun register(user: UserRegisterData) {
        view?.showProgressBar()

        registerUser.execute(user,object: IBaseUseCaseCallBack<User> {
            override fun onSuccess(data: User?) {
                val loggeduser = data!!
                if(user.image != null && user.image != Uri.EMPTY) {
                    var im = DtoDocument("pp",user.image!!)
                    im.postId = loggeduser.uid
                    uploadpp.execute(im, object : IBaseUseCaseCallBack<String> {
                        override fun onSuccess(data: String?) {
                            if (!isViewAttached()) return
                            loggeduser.profilepic= data!!
                            var repo = UserRepository()
                            repo.save(loggeduser,object: IRepository.IRepositoryListener<String>{
                                override fun onSuccess(data: String) {}
                                override fun onError(error: String) {}
                            })
                            view?.navigateToMain()
                            view?.hideProgressBar()
                        }
                        override fun onError(error: String) {
                            if (!isViewAttached()) return
                            view!!.showError(error)
                        }
                    })
                }
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