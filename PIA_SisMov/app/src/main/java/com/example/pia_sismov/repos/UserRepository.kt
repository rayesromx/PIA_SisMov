package com.example.pia_sismov.repos

import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.entities.PostImage
import com.example.pia_sismov.domain.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot

class UserRepository: FireBaseRepository<User>("Users") {
    override fun getValue(item: DataSnapshot) = item.getValue(User::class.java)

    fun getCurrentLoggedUser(listener: IRepository.IRepositoryListener<User?>){
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        getById(firebaseUser!!.uid,object: IRepository.IRepositoryListener<User?>{
            override fun onSuccess(data: User?) {
                listener.onSuccess(data)
            }
            override fun onError(error: String) {
                listener.onError(error)
            }
        })
    }
}


