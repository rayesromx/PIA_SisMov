package com.example.pia_sismov.domain.interactors.register

import com.example.pia_sismov.domain.entities.User
import com.example.pia_sismov.domain.interactors.IBaseUseCaseCallBack
import com.example.pia_sismov.domain.interactors.IRegisterUserUseCase
import com.example.pia_sismov.domain.mappers.register.GetUserFromUserRegister
import com.example.pia_sismov.presentation.account.model.UserRegisterData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase


class RegisterUser: IRegisterUserUseCase {
    override fun execute(input: UserRegisterData, listener: IBaseUseCaseCallBack<User>) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(input.email,input.pass1).addOnCompleteListener{
            if(it.isSuccessful){
                val firebaseUserID = FirebaseAuth.getInstance().currentUser?.uid
                val refUser = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID!!)

                val user = GetUserFromUserRegister(input).get()
                user.uid = firebaseUserID

                refUser.updateChildren(user.getHastMap()).addOnCompleteListener{task->
                    if(task.isSuccessful){
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(user.name).build()
                        FirebaseAuth.getInstance().currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener{ itUpdate ->
                            if(itUpdate.isSuccessful){
                                listener.onSuccess(user)
                            }else{
                                listener.onError(itUpdate.exception?.message!!)
                            }
                        }
                    }else{
                        listener.onError(task.exception?.message.toString())
                    }
                }
            }else{
                listener.onError(it.exception?.message.toString())
            }
        }
    }
}