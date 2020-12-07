package com.example.pia_sismov.domain.interactors.posts

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.IGetAllPublishedPostsFromOtherUseCase
import com.example.pia_sismov.repos.IRepository
import com.example.pia_sismov.repos.PostRepository

class GetAllPublishedPostsFromOthers(
    private val repository: PostRepository
) : IGetAllPublishedPostsFromOtherUseCase {
    override fun execute(listener: IBaseUseCaseCallBack<List<Post>>) {
        repository.getAllPublishedPostFromOthers(object: IRepository.IRepositoryListener<List<Post>>{
            override fun onSuccess(data: List<Post>) {
                listener.onSuccess(data)
            }

            override fun onError(error: String) {
                listener.onError(error)
            }
        })
    }
}