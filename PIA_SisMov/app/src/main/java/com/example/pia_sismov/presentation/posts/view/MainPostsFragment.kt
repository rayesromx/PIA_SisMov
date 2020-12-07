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
import com.example.pia_sismov.domain.interactors.posts.GetAllPublishedPostsFromUser
import com.example.pia_sismov.presentation.posts.IMainPostsFragmentContract
import com.example.pia_sismov.presentation.posts.adapters.MainPostsFragmentAdapter
import com.example.pia_sismov.presentation.posts.presenter.MainPostsPresenter
import com.example.pia_sismov.repos.PostRepository
import fcfm.lmad.poi.ChatPoi.presentation.shared.view.BaseFragment
import kotlinx.android.synthetic.main.main_posts_fragment.view.*

class MainPostsFragment(
    private val ctx: Context
) : BaseFragment<IMainPostsFragmentContract.IView, MainPostsPresenter>(ctx),
    IMainPostsFragmentContract.IView {

    lateinit var adapter: MainPostsFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        presenter.loadAllUserPosts()
        return rootView
    }

    override fun onPostsLoaded(posts:List<Post>){
        adapter = MainPostsFragmentAdapter(posts,this)
        rootView.rvMainChatFrag.layoutManager = LinearLayoutManager(ctx)
        rootView.rvMainChatFrag.adapter = adapter
    }

    override fun onViewUser(userid: String) {
        CustomSessionState.userIdFromPost = userid
        ctx.startActivity(Intent(ctx, UserproigfilkeActivity::class.java))
    }

    override fun getFragmentLayoutID() = R.layout.main_posts_fragment

    override fun instantiatePresenter() =
        MainPostsPresenter(
            GetAllPublishedPostsFromUser(
            PostRepository()
        ))

    override fun onPostSelected(post: Post) {
        CustomSessionState.currentPost = post
        CustomSessionState.isEditingPost = false
        val intent = Intent(ctx, PostDetailActivity::class.java)
        ctx.startActivity(intent)
    }

}