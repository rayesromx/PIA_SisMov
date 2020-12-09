package com.example.pia_sismov.presentation.posts

import com.example.pia_sismov.presentation.posts.model.DtoDocument
import com.example.pia_sismov.presentation.posts.model.NewPost
import com.example.pia_sismov.presentation.shared.IBasePresenter
import com.example.pia_sismov.presentation.shared.IBaseView

interface INewPostContract {
    interface IView: IBaseView {
        fun loadImage()
        fun loadFile()
        fun onUpdatedImageRV()
        fun onImageDeleted(img:DtoDocument)
        fun publishPost()
        fun publishDraft()
        fun finishFrag()
    }

    interface IPresenter: IBasePresenter<IView> {
        fun addImageToList(image: DtoDocument)
        fun removeImageFromList(image: DtoDocument)
        fun loadFile(file: DtoDocument)
        fun publish(post: NewPost)
    }
}