package com.example.pia_sismov.domain.mappers.register

import com.example.pia_sismov.domain.entities.User
import com.example.pia_sismov.presentation.account.model.UserRegisterData

class GetUserFromUserRegister(
    private val userRegister:UserRegisterData
) {
    fun get(): User {
        val user = User()
        user.name = userRegister.name
        user.lastName = userRegister.lastName
        user.email = userRegister.email
        user.phone = userRegister.phone
        return user
    }
}