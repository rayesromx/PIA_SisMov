package com.example.pia_sismov.domain.interactors

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.entities.PostImage
import com.example.pia_sismov.domain.entities.User
import com.example.pia_sismov.presentation.account.model.LoginData
import com.example.pia_sismov.presentation.account.model.UserRegisterData
import com.example.pia_sismov.presentation.posts.model.EditableImage
import com.example.pia_sismov.presentation.posts.model.NewPost

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


///login interactors

interface ICheckLoggedInUseCase: IBaseUseCase<Boolean> {}
interface ILogOutUseCase: IBaseUseCase<Boolean> {}
interface ILogInUseCase: IBaseUseCaseWithInput<LoginData, Boolean> {}
interface IGetLoggedUserDataUseCase: IBaseUseCase<User> {}

//post interactor
interface ICreateNewPostUseCase: IBaseUseCaseWithInput<NewPost, Post> {}
interface ICreateNewDocumentUseCase: IBaseUseCaseWithInput<EditableImage, PostImage> {}
//register interactors

interface IRegisterUserUseCase: IBaseUseCaseWithInput<UserRegisterData, Boolean> {}

//user interactors

interface IGetLoggedUserUseCase: IBaseUseCase<User> {}
interface IListAllUsersUseCase: IBaseUseCase<List<User>> {}
interface ISearchUserByUsernameUseCase: IBaseUseCaseWithInput<String,List<User>> {}
interface ISearchUserByIdUseCase: IBaseUseCaseWithInput<String,User> {}
interface IUpdateUserUseCase: IBaseUseCaseWithInput<User,User> {}
