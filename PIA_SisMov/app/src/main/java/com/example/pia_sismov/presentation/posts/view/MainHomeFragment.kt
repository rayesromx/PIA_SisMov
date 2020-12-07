package com.example.pia_sismov.presentation.posts.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pia_sismov.R
import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.interactors.posts.GetAllPublishedPostsFromOthers
import com.example.pia_sismov.presentation.posts.IMainHomeFragmentContract
import com.example.pia_sismov.presentation.posts.adapters.MainHomeAdapter
import com.example.pia_sismov.presentation.posts.presenter.MainHomePresenter
import com.example.pia_sismov.repos.PostRepository
import fcfm.lmad.poi.ChatPoi.presentation.shared.view.BaseFragment
import kotlinx.android.synthetic.main.main_home_fragment.view.*


class MainHomeFragment(
    private val ctx: Context
) : BaseFragment<IMainHomeFragmentContract.IView, MainHomePresenter>(ctx),IMainHomeFragmentContract.IView {

    lateinit var adapter: MainHomeAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        presenter.loadAllPublishedPosts()
        return rootView
    }

    override fun onPostsLoaded(posts:List<Post>){
        adapter = MainHomeAdapter(posts, this)
        rootView.rvMainAllPostsFrag.layoutManager = LinearLayoutManager(ctx)
        rootView.rvMainAllPostsFrag.adapter = adapter
    }

    override fun getFragmentLayoutID() = R.layout.main_home_fragment

    override fun instantiatePresenter() =
        MainHomePresenter(GetAllPublishedPostsFromOthers(
            PostRepository()
        ))

    override fun onPostSelected(post: Post) {
        val intent = Intent(ctx, PostDetailActivity::class.java)
        ctx.startActivity(intent)
    }

}