package com.example.pia_sismov.presentation.posts.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pia_sismov.CustomSessionState
import com.example.pia_sismov.R
import fcfm.lmad.poi.ChatPoi.presentation.shared.view.BaseFragment
import kotlinx.android.synthetic.main.main_profile_fragment.view.*

class MainProfileFragment(
    private val ctx: Context
) : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //rootView = inflater.inflate(R.layout.main_profile_fragment, container, false)
        var view = inflater.inflate(R.layout.main_profile_fragment, container, false)//rootView

        view.profile_email.setText(CustomSessionState.currentUser.email)
        view.profile_name.setText(CustomSessionState.currentUser.name + " " + CustomSessionState.currentUser.lastName)
        if(CustomSessionState.currentUser.phone.isNullOrBlank())
            view.profile_phone.setText("N/D")
        else
            view.profile_phone.setText(CustomSessionState.currentUser.phone)
        return view
    }

}