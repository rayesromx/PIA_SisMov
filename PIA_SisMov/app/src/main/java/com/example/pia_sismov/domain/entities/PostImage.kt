package com.example.pia_sismov.domain.entities

class PostImage: BaseEntity() {
    var postId : String = ""
    var type : String = ""
    var url : String = ""
    override fun getHastMap():HashMap<String,Any?>{
        val messageHashMap = HashMap<String, Any?>()
        messageHashMap["uid"] = uid
        messageHashMap["postId"] = postId
        messageHashMap["type"] = type
        messageHashMap["url"] = url
        return messageHashMap
    }
}

