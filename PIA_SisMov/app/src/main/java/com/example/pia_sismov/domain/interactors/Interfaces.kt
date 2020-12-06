package com.example.pia_sismov.domain.interactors

import com.example.pia_sismov.presentation.account.model.UserRegisterData

interface IBaseUseCaseCallBack<Response> {
    fun onSuccess(data: Response?)
    fun onError(error: String)
}

interface IBaseUseCaseWithInput<Request,Response> {
    fun execute(input:Request,listener:IBaseUseCaseCallBack<Response>)
}

interface IBaseUseCase<Response> {
    fun execute(listener:IBaseUseCaseCallBack<Response>)
}



//register interactors

interface IRegisterUserUseCase: IBaseUseCaseWithInput<UserRegisterData, Boolean> {}