package com.example.pia_sismov.domain.interactors.login

import com.example.pia_sismov.domain.entities.User
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.IGetLoggedUserDataUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GetLoggedUserData: IGetLoggedUserDataUseCase {
    override fun execute(listener: IBaseUseCaseCallBack<User>) {
        var user: User
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)

        refUsers.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                listener.onSuccess(user)
            }
            override fun onCancelled(error: DatabaseError) {
                listener.onError(error.message)
            }
        })
    }
}