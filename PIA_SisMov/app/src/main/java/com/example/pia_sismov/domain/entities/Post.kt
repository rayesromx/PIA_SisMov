package com.example.pia_sismov.domain.entities

class Post: BaseEntity() {

    var createdBy: String = ""
    var title: String = ""
    var description: String = ""
    var document_url: String = ""
    var isPublished: Boolean = false

    override fun getHastMap():HashMap<String,Any?>{
        val messageHashMap = HashMap<String, Any?>()
        messageHashMap["uid"] = uid
        messageHashMap["createdBy"] =  createdBy
        messageHashMap["title"] =  title
        messageHashMap["description"] = description
        messageHashMap["document_url"] = document_url
        messageHashMap["isPublished"] = isPublished
        return messageHashMap
    }
}

