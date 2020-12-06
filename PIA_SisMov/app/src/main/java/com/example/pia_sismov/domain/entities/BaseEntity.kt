package com.example.pia_sismov.domain.entities

abstract class BaseEntity {
    var uid: String = ""
    abstract fun getHastMap():HashMap<String,Any?>
}