package com.example.pia_sismov.domain.interactors.posts

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.IGetAllDraftPostsFromUserUseCase
import com.example.pia_sismov.repos.IRepository
import com.example.pia_sismov.repos.PostRepository

class GetAllDraftPostsFromUser(
    private val repository: PostRepository
) : IGetAllDraftPostsFromUserUseCase {
    override fun execute(listener: IBaseUseCaseCallBack<List<Post>>) {
        repository.getAllDraftsPostFromUser(object: IRepository.IRepositoryListener<List<Post>>{
            override fun onSuccess(data: List<Post>) {
                listener.onSuccess(data)
            }

            override fun onError(error: String) {
                listener.onError(error)
            }
        })
    }
}