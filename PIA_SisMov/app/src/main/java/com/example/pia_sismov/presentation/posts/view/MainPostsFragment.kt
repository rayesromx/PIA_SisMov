package com.example.pia_sismov.presentation.posts.view
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pia_sismov.R
import com.example.pia_sismov.presentation.posts.adapters.MainPostsFragmentAdapter

class MainPostsFragment(
) : Fragment() {

    private lateinit var rootView: View
    lateinit var adapter: MainPostsFragmentAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.main_posts_fragment, container, false)
        //adapter = MainPostsFragmentAdapter(viewModel.mineList,fragAdmin)
        //rootView.rvMainChatFrag.adapter = adapter
        return rootView
    }

}

