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
import kotlinx.android.synthetic.main.main_home_fragment.view.*
import kotlinx.android.synthetic.main.main_posts_fragment.*
import kotlinx.android.synthetic.main.main_posts_fragment.view.*
import java.lang.Exception

class MainPostsFragment(
    var ctx: Context,
    var fragManager: IFragManager
) : Fragment() {

    private lateinit var rootView: View
    lateinit var adapter: MainPostsFragmentAdapter
    val postsMineList = mutableListOf<Post>()
    val postsDraftList = mutableListOf<Post>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.main_posts_fragment  , container, false)
        loadPostsLists()

        fragManager.changeFragment(R.id.mainPostsFrameContainer, PostFragment(postsMineList,fragManager), "PostMineFragment")
        rootView.tabPostsLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> fragManager.changeFragment(R.id.mainPostsFrameContainer, PostFragment(postsMineList,fragManager), "PostMineFragment")
                    1 -> fragManager.changeFragment(R.id.mainPostsFrameContainer,PostFragment(postsDraftList,fragManager), "PostDraftFragment")
                    else -> fragManager.changeFragment(R.id.mainPostsFrameContainer,PostFragment(postsMineList,fragManager), "PostMineFragment")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        return rootView
    }


    fun loadPostsLists()
    {
        for(i in 1..10) {
            postsMineList.add(Post("Objeto perdido #$i", "", "Ayuda pa encontar el obj !$i"))
            postsDraftList.add(Post("Draft obj perdido #$i", "", "Draft del obj !$i"))
        }
    }

}

class HomeFragment(
    var fragManager: IFragManager
) : Fragment() {

    private lateinit var rootView: View
    lateinit var adapter: PostFragmentAdapter
    val postsMineList = mutableListOf<Post>()
    val postsDraftList = mutableListOf<Post>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.main_home_fragment, container, false)
        loadPostsLists()
        adapter = PostFragmentAdapter(postsMineList,fragManager)
        rootView.rvAllPosts.adapter = adapter
        return rootView
    }

    fun loadPostsLists()
    {
        for(i in 1..10) {
            postsMineList.add(Post("Objeto perdido #$i", "", "Ayuda pa encontar el obj !$i"))
            postsDraftList.add(Post("Draft obj perdido #$i", "", "Draft del obj !$i"))
        }
    }
}