package com.example.pia_sismov.presentation.posts.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pia_sismov.R
import com.example.pia_sismov.presentation.posts.adapters.DraftPostFragmentAdapter

class MainDraftsFragment (
     // private val ctx: Context
) : Fragment() {

    private lateinit var rootView: View
    lateinit var adapter: DraftPostFragmentAdapter
    //private lateinit var viewModel: MainAllPostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.main_drafts_fragment, container, false)
        // initializeVM()

        // rootView.btnAddNewTeamChat.setOnClickListener {fragAdmin.launchActivity(6)}

        //adapter = DraftPostFragmentAdapter(viewModel.postsDraftList,fragAdmin)
        //rootView.rvMainDraftsFrag.adapter = adapter
        return rootView
    }


}