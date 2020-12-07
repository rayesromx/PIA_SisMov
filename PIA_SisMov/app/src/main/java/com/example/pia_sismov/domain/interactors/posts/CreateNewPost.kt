package com.example.pia_sismov.domain.interactors.posts

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.entities.PostImage
import com.example.pia_sismov.domain.interactors.*
import com.example.pia_sismov.presentation.posts.model.NewPost
import com.example.pia_sismov.repos.IRepository
import com.example.pia_sismov.repos.PostImageRepository
import com.example.pia_sismov.repos.PostRepository

class CreateNewPost(
    private val repository: PostRepository
) : ICreateNewPostUseCase {
    override fun execute(input: NewPost, listener: IBaseUseCaseCallBack<Post>) {

        val post = Post()
        post.title = input.title
        post.description = input.description
        post.createdBy = input.userid
        post.datePublished = input.isPublished

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

class SavePost(
    private val repository: PostRepository,
    private val pirepository: PostImageRepository
) : ISavePostUseCase {
    override fun execute(input: Post, listener: IBaseUseCaseCallBack<Post>) {
        repository.save(input,object: IRepository.IRepositoryListener<String>{
            override fun onSuccess(data: String) {
                pirepository.getAlll(object:IRepository.IRepositoryListener<List<PostImage>>{
                    override fun onSuccess(data: List<PostImage>) {
                        for (doc in data){
                            if(doc.postId == input.uid)
                                pirepository.deleteById(doc.uid)
                        }
                        listener.onSuccess(input)
                    }
                    override fun onError(error: String) {
                        listener.onError(error)
                    }
                })
            }

            override fun onError(error: String) {
                listener.onError(error)
            }
        })
    }
}