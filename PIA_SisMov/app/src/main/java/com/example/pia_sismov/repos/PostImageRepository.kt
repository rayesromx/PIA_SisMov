package com.example.pia_sismov.repos

import com.example.pia_sismov.domain.entities.PostImage
import com.google.firebase.database.DataSnapshot

class PostImageRepository: FireBaseRepository<PostImage>("PostImagees") {
    override fun getValue(item: DataSnapshot) = item.getValue(PostImage::class.java)
}