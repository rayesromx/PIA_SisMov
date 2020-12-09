package com.example.pia_sismov.presentation.posts.model

data class NewPost (
    var userid:String = "",
    val title:String = "",
    val description: String= "",
    val imageList: List<DtoDocument>,
    val document: DtoDocument,
    val isPublished:String = ""
)
