package com.example.pia_sismov.presentation.posts.presenter

import android.net.Uri
import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.entities.PostImage
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.posts.CreateNewDocument
import com.example.pia_sismov.domain.interactors.posts.GetAllImagesFromPost
import com.example.pia_sismov.domain.interactors.posts.SavePost
import com.example.pia_sismov.presentation.posts.IPostDetailContract
import com.example.pia_sismov.presentation.posts.model.DtoDocument
import com.example.pia_sismov.presentation.shared.presenter.BasePresenter
import java.lang.Exception
import java.time.LocalDateTime

class PostDetailPresenter(
    var getAllImagesFromPost: GetAllImagesFromPost,
    val savePost: SavePost,
    val createNewDocument: CreateNewDocument
): BasePresenter<IPostDetailContract.IView>(), IPostDetailContract.IPresenter {

    var imageList = ArrayList<DtoDocument>()
    var file: DtoDocument? = DtoDocument("doc",Uri.EMPTY)

    override fun loadImageFromPost(post: Post){
        getAllImagesFromPost.execute(post, object: IBaseUseCaseCallBack<List<PostImage>> {
            override fun onSuccess(data: List<PostImage>?) {
                if(!isViewAttached()) return
                imageList.clear()
                for (item in data!!){
                    val ei = DtoDocument(
                        item.type,
                        Uri.EMPTY,
                        item.uid,
                        item.postId,
                        item.url,
                        null,
                        item.filename
                    )
                    if(item.type == "doc")
                      file = ei
                    else
                        imageList.add(ei)
                }
                view!!.onUpdatedImageRV()
            }

            override fun onError(error: String) {
                if(!isViewAttached()) return
                view!!.showError(error)
            }
        })
    }

    override fun addImageToList(image: DtoDocument) {
        imageList.add(image)
        view!!.onUpdatedImageRV()
    }
    override fun removeImageFromList(image: DtoDocument) {
        imageList.remove(image)
        view!!.onUpdatedImageRV()
    }
    override fun loadFile(file: DtoDocument) {
        this.file = file
    }

    override fun onPostSaved(post: Post) {
        post.datePublished =""
        try {
            val parsedInt =  post.uid.toInt()
            post.uid =""
        }catch(ex:Exception){

        }

        guardarPost(post)
    }

    override fun onPostLoaded(post: Post) {
        post.datePublished =  LocalDateTime.now().toString()
        try {
            val parsedInt =  post.uid.toInt()
            post.uid =""
        }catch(ex:Exception){

        }
        guardarPost(post)
    }

    private fun guardarPost(post:Post){
        savePost.execute(post, object:IBaseUseCaseCallBack<Post>{
            override fun onSuccess(data: Post?) {
                if(!isViewAttached()) return

                if(file != null && file!!.uri != Uri.EMPTY) {
                    file!!.postId = data!!.uid
                    createNewDocument.execute(file!!, object : IBaseUseCaseCallBack<PostImage> {
                        override fun onSuccess(data: PostImage?) {
                            if (!isViewAttached()) return
                            view!!.showError("Documento guardada exitosamente")
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
                            view!!.showError("Imagen guardado exitosamente")
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
