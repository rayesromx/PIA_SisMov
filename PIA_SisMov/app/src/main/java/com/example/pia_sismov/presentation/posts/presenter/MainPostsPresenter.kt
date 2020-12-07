package com.example.pia_sismov.presentation.posts.presenter

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.posts.GetAllPublishedPostsFromUser
import com.example.pia_sismov.presentation.posts.IMainPostsFragmentContract
import com.example.pia_sismov.presentation.shared.presenter.BasePresenter


class MainPostsPresenter(
    private val getAll : GetAllPublishedPostsFromUser
): BasePresenter<IMainPostsFragmentContract.IView>(), IMainPostsFragmentContract.IPresenter {
    override fun loadAllUserPosts() {
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
