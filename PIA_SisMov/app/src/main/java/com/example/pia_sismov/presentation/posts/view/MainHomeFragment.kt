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
import com.example.pia_sismov.domain.interactors.posts.GetAllPublishedPostsFromOthers
import com.example.pia_sismov.presentation.posts.IMainHomeFragmentContract
import com.example.pia_sismov.presentation.posts.adapters.MainHomeAdapter
import com.example.pia_sismov.presentation.posts.presenter.MainHomePresenter
import com.example.pia_sismov.repos.PostRepository
import fcfm.lmad.poi.ChatPoi.presentation.shared.view.BaseFragment
import kotlinx.android.synthetic.main.main_home_fragment.view.*

class MainHomeFragment(
    private val ctx: Context
) : BaseFragment<IMainHomeFragmentContract.IView, MainHomePresenter>(ctx) ,IMainHomeFragmentContract.IView {

    lateinit var adapter: MainHomeAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        presenter.loadAllPublishedPosts()
        if(!CustomSessionState.hayInternet){
            rootView.txtNODATAh.visibility = View.VISIBLE
            rootView.txtNODATAh.text = "Sin internet"
        }

        rootView.btn_search_posts.setOnClickListener{
            var postsToBeFilters = ArrayList<Post>()
            var filterParameter = rootView.etxt_name_search.text.toString().toLowerCase()
            for(post in presenter.postsToBeFiltered){
                if(post.title.toLowerCase().contains(filterParameter) || post.description.toLowerCase().contains(filterParameter))
                    postsToBeFilters.add(post)
            }
            onPostsLoaded(postsToBeFilters)
        }
        return rootView
    }

    override fun onPostsLoaded(posts:List<Post>){
        if(posts.isEmpty())
            rootView.txtNODATAh.visibility = View.VISIBLE
        else
            rootView.txtNODATAh.visibility = View.GONE
        adapter = MainHomeAdapter(posts, this)
        rootView.rvMainAllPostsFrag.layoutManager = LinearLayoutManager(ctx)
        rootView.rvMainAllPostsFrag.adapter = adapter
    }

    override fun onViewUser(userid: String) {
        CustomSessionState.userIdFromPost = userid
        ctx.startActivity(Intent(ctx, UserproigfilkeActivity::class.java))
    }

    override fun getFragmentLayoutID() = R.layout.main_home_fragment

    override fun instantiatePresenter() =
        MainHomePresenter(GetAllPublishedPostsFromOthers(
            PostRepository()
        ))

    override fun onPostSelected(post: Post) {
        CustomSessionState.currentPost = post
        CustomSessionState.isEditingPost = false

        val intent = Intent(ctx, PostDetailActivity::class.java)
        ctx.startActivity(intent)
    }
}