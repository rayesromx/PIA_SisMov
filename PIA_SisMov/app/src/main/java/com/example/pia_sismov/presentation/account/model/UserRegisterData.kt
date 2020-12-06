package com.example.pia_sismov.presentation.account.model

data class UserRegisterData (
    var name: String = "",
    var lastName: String = "",
    var email: String = "",
    var pass1: String = "",
    var pass2: String = "",
    var phone: String = "",
)