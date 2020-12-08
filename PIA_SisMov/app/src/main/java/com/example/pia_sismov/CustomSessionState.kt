package com.example.pia_sismov

import android.content.Context
import android.net.ConnectivityManager
import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.entities.User

object CustomSessionState {
    var currentUser: User = User()
    var currentPost: Post = Post()
    var isEditingPost = false
    var hayInternet = false
    var userIdFromPost:String = ""


    fun isConnectedToNetwork(ctx:Context): Boolean {
        val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting() ?: false
    }
}