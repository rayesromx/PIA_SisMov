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
    }

    interface IPresenter: IBasePresenter<IView> {
        fun loadAllPublishedPosts()
    }
}

interface IMainDraftsFragmentContract {
    interface IView: IBaseView {
        fun onPostSelected(post: Post)
        fun onPostsLoaded(posts:List<Post>)
    }

    interface IPresenter: IBasePresenter<IView> {
        fun loadAllUserDraftPosts()
    }
}

interface IMainPostsFragmentContract {
    interface IView: IBaseView {
        fun onPostSelected(post: Post)
        fun onPostsLoaded(posts:List<Post>)
    }

    interface IPresenter: IBasePresenter<IView> {
        fun loadAllUserPosts()
    }
}

interface IMainProfileFragmentContract {
    interface IView: IBaseView {

    }

    interface IPresenter: IBasePresenter<IView> {

    }
}