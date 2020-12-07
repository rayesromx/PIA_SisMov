package com.example.pia_sismov.presentation.posts.model

import android.net.Uri
import java.util.*

data class EditableImage (
    val type:String,
    var uri:Uri,
    val id:String = UUID.randomUUID().toString(),
    var postId :String = "",
    var url:String = ""
)