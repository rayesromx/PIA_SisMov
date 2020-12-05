package com.example.pia_sismov.presentation.posts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pia_sismov.R
import com.example.pia_sismov.presentation.posts.adapters.MainHomeAdapter


class MainHomeFragment(
) : Fragment() {

    private lateinit var rootView: View
    lateinit var adapter: MainHomeAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.main_home_fragment, container, false)
  //      adapter = MainHomeAdapter(viewModel.postsMineList,fragAdmin)
//        rootView.rvMainAllPostsFrag.adapter = adapter
        return rootView
    }

}