package com.example.pia_sismov.presentation.posts.model

import android.net.Uri

data class NewPost (
    var userid:String = "",
    val title:String = "",
    val description: String= "",
    val imageList: List<EditableImage>,
    val document: EditableImage,
    val isPublished:Boolean = false
)
