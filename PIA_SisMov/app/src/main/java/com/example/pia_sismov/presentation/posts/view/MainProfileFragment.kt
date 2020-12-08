package com.example.pia_sismov.presentation.posts.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pia_sismov.CustomSessionState
import com.example.pia_sismov.R
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.login.LogOut
import com.example.pia_sismov.presentation.main.IMainContract
import com.squareup.picasso.Picasso
import fcfm.lmad.poi.ChatPoi.presentation.shared.view.BaseFragment
import kotlinx.android.synthetic.main.main_profile_fragment.view.*

class MainProfileFragment(
    private val ctx: Context,
    private val parentView: IMainContract.IView
) : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_profile_fragment, container, false)

        view.btn_logout.setOnClickListener{
            val logOut = LogOut()
            logOut.execute(object: IBaseUseCaseCallBack<Boolean> {
                override fun onSuccess(data: Boolean?) {

                }
                override fun onError(error: String) {
                }
            })
            parentView.finishActivity()
        }

        if(!CustomSessionState.currentUser.profilepic.isBlank()){
            Picasso.get().load(CustomSessionState.currentUser.profilepic).into(view.profilepic)
        }

        view.profile_email.setText(CustomSessionState.currentUser.email)
        view.profile_name.setText(CustomSessionState.currentUser.name + " " + CustomSessionState.currentUser.lastName)
        if(CustomSessionState.currentUser.phone.isNullOrBlank())
            view.profile_phone.setText("N/D")
        else
            view.profile_phone.setText(CustomSessionState.currentUser.phone)
        return view
    }

}