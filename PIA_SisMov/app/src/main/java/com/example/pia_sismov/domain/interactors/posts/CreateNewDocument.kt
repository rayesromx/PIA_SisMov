package com.example.pia_sismov.domain.interactors.posts

import android.graphics.Bitmap
import android.net.Uri
import com.example.pia_sismov.domain.entities.PostImage
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.ICreateNewDocumentUseCase
import com.example.pia_sismov.domain.interactors.IUploadImagePPUseCase
import com.example.pia_sismov.presentation.posts.model.EditableImage
import com.example.pia_sismov.repos.IRepository
import com.example.pia_sismov.repos.PostImageRepository
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

class CreateNewDocument (
    private val repository: PostImageRepository
) : ICreateNewDocumentUseCase {
    override fun execute(input: EditableImage, listener: IBaseUseCaseCallBack<PostImage>) {

        if(input.url.isBlank()){
            val storageReference = FirebaseStorage.getInstance().reference
                .child("Files").child(input.postId).child(input.id)

            val uploadTask: StorageTask<*>
            if(!input.uri.toString().isBlank())
                uploadTask = storageReference.putFile(input.uri)
            else{
                val baos = ByteArrayOutputStream()
                input.bmpImage?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                uploadTask = storageReference.putBytes(data)
            }
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
                    post.type = input.type

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


class UploadPP (
    private val repository: PostImageRepository
) : IUploadImagePPUseCase {
    override fun execute(input: EditableImage, listener: IBaseUseCaseCallBack<String>) {
        val storageReference = FirebaseStorage.getInstance().reference
            .child("Files").child("UsersPPs").child(input.postId)

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
                listener.onSuccess(url)
            }
        }
    }
}