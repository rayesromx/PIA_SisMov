package com.example.pia_sismov.presentation.posts.presenter

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.posts.GetAllPublishedPostsFromOthers
import com.example.pia_sismov.presentation.posts.IMainHomeFragmentContract
import com.example.pia_sismov.presentation.shared.presenter.BasePresenter

class MainHomePresenter(
    private val getAll : GetAllPublishedPostsFromOthers
): BasePresenter<IMainHomeFragmentContract.IView>(), IMainHomeFragmentContract.IPresenter {
    val postsToBeFiltered = ArrayList<Post>()

    override fun loadAllPublishedPosts() {
        getAll.execute(object: IBaseUseCaseCallBack<List<Post>> {
            override fun onSuccess(data: List<Post>?) {
                if(!isViewAttached()) return
                postsToBeFiltered.clear()
                postsToBeFiltered.addAll(data!!)
                view!!.onPostsLoaded(data!!)
            }

            override fun onError(error: String) {
                if(!isViewAttached()) return
                view!!.showError(error)
            }
        })
    }

}