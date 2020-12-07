package com.example.pia_sismov.presentation.posts

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.presentation.posts.model.EditableImage
import com.example.pia_sismov.presentation.posts.model.NewPost
import com.example.pia_sismov.presentation.shared.IBasePresenter
import com.example.pia_sismov.presentation.shared.IBaseView

interface IMainHomeFragmentContract {
    interface IView: IBaseView {
        fun onPostSelected(post: Post)
        fun onPostsLoaded(posts:List<Post>)
        fun onViewUser(userid:String)
    }

    interface IPresenter: IBasePresenter<IView> {
        fun loadAllPublishedPosts()
    }
}

interface IMainDraftsFragmentContract {
    interface IView: IBaseView {
        fun onPostSelected(post: Post)
        fun onPostsLoaded(posts:List<Post>)
        fun onViewUser(userid:String)
    }

    interface IPresenter: IBasePresenter<IView> {
        fun loadAllUserDraftPosts()
    }
}

interface IMainPostsFragmentContract {
    interface IView: IBaseView {
        fun onPostSelected(post: Post)
        fun onPostsLoaded(posts:List<Post>)
        fun onViewUser(userid:String)
    }

    interface IPresenter: IBasePresenter<IView> {
        fun loadAllUserPosts()
    }
}

interface IPostDetailContract {
    interface IView: IBaseView {
        fun onImageDeleted(img:EditableImage)
        fun onUpdatedImageRV()
        fun finishFrag()
    }

    interface IPresenter: IBasePresenter<IView> {
        fun loadImageFromPost(post:Post)
        fun addImageToList(image: EditableImage)
        fun removeImageFromList(image: EditableImage)
        fun onPostSaved(post: Post)
        fun onPostLoaded(post: Post)
        fun loadFile(file: EditableImage)
    }
}