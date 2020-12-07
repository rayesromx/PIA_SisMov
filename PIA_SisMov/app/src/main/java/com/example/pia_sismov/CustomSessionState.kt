package com.example.pia_sismov

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.entities.User

object CustomSessionState {
    var currentUser: User = User()
    var currentPost: Post = Post()
    var isEditingPost = false
    var hayInteret = false
    var userIdFromPost:String = ""
}