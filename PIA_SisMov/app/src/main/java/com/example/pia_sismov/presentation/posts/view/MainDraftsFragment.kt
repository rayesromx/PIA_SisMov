package com.example.pia_sismov.presentation.posts.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pia_sismov.CustomSessionState
import com.example.pia_sismov.R
import com.example.pia_sismov.UserproigfilkeActivity
import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.interactors.posts.GetAllDraftPostsFromUser
import com.example.pia_sismov.presentation.posts.IMainDraftsFragmentContract
import com.example.pia_sismov.presentation.posts.adapters.DraftPostFragmentAdapter
import com.example.pia_sismov.presentation.posts.presenter.MainDraftPresenter
import com.example.pia_sismov.repos.PostRepository
import fcfm.lmad.poi.ChatPoi.presentation.shared.view.BaseFragment
import kotlinx.android.synthetic.main.main_drafts_fragment.view.*

class MainDraftsFragment(
    private val ctx: Context
) : BaseFragment<IMainDraftsFragmentContract.IView, MainDraftPresenter>(ctx),
    IMainDraftsFragmentContract.IView {

    lateinit var adapter: DraftPostFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        presenter.loadAllUserDraftPosts()
        return rootView
    }

    override fun onPostsLoaded(posts:List<Post>){
        adapter = DraftPostFragmentAdapter(posts, this)
        rootView.rvMainDraftsFrag.layoutManager = LinearLayoutManager(ctx)
        rootView.rvMainDraftsFrag.adapter = adapter
    }

    override fun onViewUser(userid: String) {
        CustomSessionState.userIdFromPost = userid
        ctx.startActivity(Intent(ctx, UserproigfilkeActivity::class.java))
    }

    override fun getFragmentLayoutID() = R.layout.main_drafts_fragment

    override fun instantiatePresenter() =
        MainDraftPresenter(GetAllDraftPostsFromUser(
            PostRepository()
        ))

    override fun onPostSelected(post: Post) {
        CustomSessionState.currentPost = post
        CustomSessionState.isEditingPost = true
        val intent = Intent(ctx, PostDetailActivity::class.java)
        ctx.startActivity(intent)
    }
}