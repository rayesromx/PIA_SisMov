package com.example.pia_sismov.domain.interactors.posts

import android.net.Uri
import com.example.pia_sismov.domain.entities.PostImage
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.ICreateNewDocumentUseCase
import com.example.pia_sismov.presentation.posts.model.EditableImage
import com.example.pia_sismov.repos.IRepository
import com.example.pia_sismov.repos.PostImageRepository
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask

class CreateNewDocument (
    private val repository: PostImageRepository
) : ICreateNewDocumentUseCase {
    override fun execute(input: EditableImage, listener: IBaseUseCaseCallBack<PostImage>) {

        if(input.url.isBlank()){
            val storageReference = FirebaseStorage.getInstance().reference
                .child("Files").child(input.postId).child(input.id)

            val uploadTask: StorageTask<*>
            uploadTask = storageReference.putFile(input.uri!!)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{ task->
                if(!task.isSuccessful){
                    listener.onError(task.exception?.message!!)
                    task.exception?.let{
                        throw it
                    }
                }
                return@Continuation storageReference.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUrl = task.result
                    val url = downloadUrl.toString()

                    val post = PostImage()
                    post.url = url
                    post.postId = input.postId

                    repository.save(post, object : IRepository.IRepositoryListener<String> {
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
        }
        else{
            val post = PostImage()
            post.url = input.url
            post.postId =  input.postId
            repository.save(post, object : IRepository.IRepositoryListener<String> {
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
}