package com.example.pia_sismov.domain.entities

class User: BaseEntity() {
    var name: String = ""
    var lastName: String = ""
    var email: String = ""
    var phone: String = ""

    override fun getHastMap():HashMap<String,Any?>{
        val messageHashMap = HashMap<String, Any?>()
        messageHashMap["uid"] = uid
        messageHashMap["name"] =  name
        messageHashMap["lastName"] =  lastName
        messageHashMap["email"] = email
        messageHashMap["phone"] = phone
        return messageHashMap
    }
}