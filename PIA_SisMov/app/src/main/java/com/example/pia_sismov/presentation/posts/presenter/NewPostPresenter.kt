package com.example.pia_sismov.presentation.posts.presenter

import android.net.Uri
import com.example.pia_sismov.CustomSessionState
import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.entities.PostImage
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.posts.CreateNewDocument
import com.example.pia_sismov.domain.interactors.posts.CreateNewPost
import com.example.pia_sismov.presentation.posts.INewPostContract
import com.example.pia_sismov.presentation.posts.model.EditableImage
import com.example.pia_sismov.presentation.posts.model.NewPost
import com.example.pia_sismov.presentation.shared.presenter.BasePresenter

class NewPostPresenter(
    val createNewPost: CreateNewPost,
    val createNewDocument: CreateNewDocument
): BasePresenter<INewPostContract.IView>(), INewPostContract.IPresenter {

    var imageList = ArrayList<EditableImage>()
    var file: EditableImage? = EditableImage("doc",Uri.EMPTY)
    override fun addImageToList(image: EditableImage) {
        imageList.add(image)
        view!!.onUpdatedImageRV()
    }

    override fun removeImageFromList(image: EditableImage) {
        imageList.remove(image)
        view!!.onUpdatedImageRV()
    }

    override fun loadFile(file: EditableImage) {
        this.file = file
    }

    override fun publish(post: NewPost) {
        createNewPost.execute(post, object:IBaseUseCaseCallBack<Post>{
            override fun onSuccess(data: Post?) {
                if(!isViewAttached()) return
                if(file != null && file!!.uri != Uri.EMPTY) {
                    file!!.postId = data!!.uid
                    createNewDocument.execute(file!!, object : IBaseUseCaseCallBack<PostImage> {
                        override fun onSuccess(data: PostImage?) {
                            if (!isViewAttached()) return
                            view!!.showError("Imagen guardada exitosamente")
                        }

                        override fun onError(error: String) {
                            if (!isViewAttached()) return
                            view!!.showError(error)
                        }
                    })
                }
                for(img in imageList){
                    img.postId = data!!.uid
                    createNewDocument.execute(img!!,object:IBaseUseCaseCallBack<PostImage>{
                        override fun onSuccess(data: PostImage?) {
                            if(!isViewAttached()) return
                            view!!.showError("Documento guardado exitosamente")
                        }

                        override fun onError(error: String) {
                            if(!isViewAttached()) return
                            view!!.showError(error)
                        }
                    })
                }
                Thread.sleep(250)
                view!!.finishFrag()
            }

            override fun onError(error: String) {
                if(!isViewAttached()) return
                view!!.showError(error)
            }

        })
    }

}