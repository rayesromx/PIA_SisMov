package com.example.pia_sismov.domain.entities

class Post: BaseEntity() {

    var createdBy: String = ""
    var createdByName: String = ""
    var title: String = ""
    var description: String = ""
    var datePublished: String = ""

    override fun getHastMap():HashMap<String,Any?>{
        val messageHashMap = HashMap<String, Any?>()
        messageHashMap["uid"] = uid
        messageHashMap["createdBy"] =  createdBy
        messageHashMap["createdByName"] =  createdByName
        messageHashMap["title"] =  title
        messageHashMap["description"] = description
        messageHashMap["datePublished"] = datePublished
        return messageHashMap
    }
}

