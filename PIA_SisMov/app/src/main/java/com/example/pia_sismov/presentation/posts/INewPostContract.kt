package com.example.pia_sismov.presentation.posts

import com.example.pia_sismov.presentation.posts.model.EditableImage
import com.example.pia_sismov.presentation.posts.model.NewPost
import com.example.pia_sismov.presentation.shared.IBasePresenter
import com.example.pia_sismov.presentation.shared.IBaseView

interface INewPostContract {
    interface IView: IBaseView {
        fun loadImage()
        fun loadFile()
        fun onUpdatedImageRV()
        fun onImageDeleted(img:EditableImage)
        fun publishPost()
        fun publishDraft()
        fun finishFrag()
    }

    interface IPresenter: IBasePresenter<IView> {
        fun addImageToList(image: EditableImage)
        fun removeImageFromList(image: EditableImage)
        fun loadFile(file: EditableImage)
        fun publish(post: NewPost)
    }
}