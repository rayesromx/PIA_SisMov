package com.example.pia_sismov.domain.interactors.posts

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.ICreateNewPostUseCase
import com.example.pia_sismov.presentation.posts.model.NewPost
import com.example.pia_sismov.repos.IRepository
import com.example.pia_sismov.repos.PostRepository

class CreateNewPost(
    private val repository: PostRepository
) : ICreateNewPostUseCase {
    override fun execute(input: NewPost, listener: IBaseUseCaseCallBack<Post>) {

        val post = Post()
        post.title = input.title
        post.description = input.description
        post.createdBy = input.userid
        post.isPublished = input.isPublished

        repository.save(post,object: IRepository.IRepositoryListener<String>{
            override fun onSuccess(data: String) {
                post.uid = data
                listener.onSuccess(post)
            }

            override fun onError(error: String) {
                listener.onError(error)
            }
        })
    }

}