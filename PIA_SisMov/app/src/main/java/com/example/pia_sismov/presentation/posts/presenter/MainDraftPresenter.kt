package com.example.pia_sismov.presentation.posts.presenter

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.posts.GetAllDraftPostsFromUser
import com.example.pia_sismov.presentation.posts.IMainDraftsFragmentContract
import com.example.pia_sismov.presentation.shared.presenter.BasePresenter

class MainDraftPresenter(
    private val getAll : GetAllDraftPostsFromUser
): BasePresenter<IMainDraftsFragmentContract.IView>(), IMainDraftsFragmentContract.IPresenter {
    override fun loadAllUserDraftPosts() {
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