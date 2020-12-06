package com.example.pia_sismov.domain.interactors.user

import com.example.pia_sismov.domain.entities.User
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.IGetLoggedUserUseCase
import com.example.pia_sismov.repos.IRepository
import com.example.pia_sismov.repos.UserRepository

class GetLoggedUser(
    private val repository: UserRepository
) : IGetLoggedUserUseCase {
    override fun execute(listener: IBaseUseCaseCallBack<User>) {
        repository.getCurrentLoggedUser(object: IRepository.IRepositoryListener<User?>{
            override fun onSuccess(data: User?) {
                listener.onSuccess(data)
            }

            override fun onError(error: String) {
                listener.onError(error)
            }
        })
    }
}