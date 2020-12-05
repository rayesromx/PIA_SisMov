package com.example.pia_sismov.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pia_sismov.IFragManager
import com.example.pia_sismov.R
import com.example.pia_sismov.adapters.MainPostsFragmentAdapter
import com.example.pia_sismov.adapters.PostFragmentAdapter
import com.example.pia_sismov.models.Post
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.layout_posts.view.*
import kotlinx.android.synthetic.main.layout_posts.view.rvPosts
import kotlinx.android.synthetic.main.main_home_fragment.view.*
import kotlinx.android.synthetic.main.main_posts_fragment.*
import kotlinx.android.synthetic.main.main_posts_fragment.view.*
import java.lang.Exception


class PostFragment(
    private val list: List<Post>,
    var fragManager: IFragManager
) : Fragment() {

    private lateinit var rootView: View
    lateinit var adapter: PostFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.layout_posts, container, false)
        adapter = PostFragmentAdapter(list,fragManager)
        rootView.rvPosts.adapter = adapter
        return rootView
    }

}
