package com.example.pia_sismov.presentation.posts.model

import android.graphics.Bitmap
import android.net.Uri
import java.util.*

data class DtoDocument (
    val type:String,
    var uri:Uri,
    val id:String = UUID.randomUUID().toString(),
    var postId :String = "",
    var url:String = "",
    var bmpImage:Bitmap? = null,
    var filename:String = "",
    var extension:String = "",
)