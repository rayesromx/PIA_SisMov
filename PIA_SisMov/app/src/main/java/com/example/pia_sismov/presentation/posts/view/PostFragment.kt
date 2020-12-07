package com.example.pia_sismov.presentation.posts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pia_sismov.R
import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.presentation.posts.adapters.PostFragmentAdapter
import kotlinx.android.synthetic.main.layout_posts.view.rvPosts


class PostFragment(
    private val list: List<Post>
) : Fragment() {

    private lateinit var rootView: View
    lateinit var adapter: PostFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.layout_posts, container, false)
        //adapter = PostFragmentAdapter(list,fragManager)
        rootView.rvPosts.adapter = adapter
        return rootView
    }

}
