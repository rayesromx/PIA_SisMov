package com.example.pia_sismov.presentation.posts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pia_sismov.R

class MainProfileFragment(
) : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.main_profile_fragment, container, false)

        return rootView
    }

}