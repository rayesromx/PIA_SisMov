package com.example.pia_sismov.domain.interactors.posts

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.entities.PostImage
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.IGetAllImagesFromPostUseCase
import com.example.pia_sismov.repos.IRepository
import com.example.pia_sismov.repos.PostImageRepository

class GetAllImagesFromPost(
    private val repository: PostImageRepository
) : IGetAllImagesFromPostUseCase {
    override fun execute(input:Post, listener: IBaseUseCaseCallBack<List<PostImage>>) {
        repository.getAllImageFromPost(input, object: IRepository.IRepositoryListener<List<PostImage>>{
            override fun onSuccess(data: List<PostImage>) {
                listener.onSuccess(data)
            }

            override fun onError(error: String) {
                listener.onError(error)
            }
        })
    }
}
