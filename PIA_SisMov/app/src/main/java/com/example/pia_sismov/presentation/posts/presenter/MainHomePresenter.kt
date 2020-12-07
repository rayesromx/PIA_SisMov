package com.example.pia_sismov.presentation.posts.presenter

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.posts.GetAllDraftPostsFromUser
import com.example.pia_sismov.domain.interactors.posts.GetAllPublishedPostsFromOthers
import com.example.pia_sismov.domain.interactors.posts.GetAllPublishedPostsFromUser
import com.example.pia_sismov.presentation.posts.IMainDraftsFragmentContract
import com.example.pia_sismov.presentation.posts.IMainHomeFragmentContract
import com.example.pia_sismov.presentation.posts.IMainPostsFragmentContract
import com.example.pia_sismov.presentation.shared.presenter.BasePresenter

class MainHomePresenter(
    private val getAll : GetAllPublishedPostsFromOthers
): BasePresenter<IMainHomeFragmentContract.IView>(), IMainHomeFragmentContract.IPresenter {
    override fun loadAllPublishedPosts() {
        getAll.execute(object: IBaseUseCaseCallBack<List<Post>> {
            override fun onSuccess(data: List<Post>?) {
                if(!isViewAttached()) return
                view!!.onPostsLoaded(data!!)
            }

            override fun onError(error: String) {
                if(!isViewAttached()) return
                view!!.showError(error)
            }
        })
    }

}