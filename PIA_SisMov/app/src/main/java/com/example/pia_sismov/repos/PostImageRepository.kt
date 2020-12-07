package com.example.pia_sismov.repos

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.entities.PostImage
import com.google.firebase.database.DataSnapshot

class PostImageRepository: FireBaseRepository<PostImage>("PostImagees") {
    override fun getValue(item: DataSnapshot) = item.getValue(PostImage::class.java)

    fun getAllImageFromPost(post: Post,listener: IRepository.IRepositoryListener<List<PostImage>>){
        getAll(object:IRepository.IRepositoryListener<List<PostImage>>{
            override fun onSuccess(data: List<PostImage>) {
                val models = ArrayList<PostImage>()
                for (model in data){
                    if(model.postId == post.uid)
                        models.add(model)
                }
                listener.onSuccess(models)
            }
            override fun onError(error: String) {
                listener.onError(error)
            }
        })
    }

}