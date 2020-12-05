package com.example.pia_sismov.models

data class Post (
    var title: String,
    var image: String,
    var message: String
)

data class Publicacion(
    val title: String,
    val image:String,
    val message:String,
    val draft:Boolean
)
