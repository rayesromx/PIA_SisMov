package com.example.pia_sismov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pia_sismov.domain.entities.User
import com.example.pia_sismov.repos.IRepository
import com.example.pia_sismov.repos.UserRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_userproigfilke.*
import kotlinx.android.synthetic.main.main_profile_fragment.view.*

class UserproigfilkeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userproigfilke)

        var ctx = this
        var repo = UserRepository()
        repo.getById(CustomSessionState.userIdFromPost, object: IRepository.IRepositoryListener<User?>{
            override fun onSuccess(data: User?) {
                profile_email.setText(data!!.email)
                profile_name.setText(data!!.name + " " + data!!.lastName)
                if(data!!.phone.isNullOrBlank())
                    profile_phone.setText("N/D")
                else
                    profile_phone.setText(data!!.phone)

                if(!data.profilepic.isBlank())
                    Picasso.get().load(data.profilepic).into(userProfilePic)
            }

            override fun onError(error: String) {
                Toast.makeText(ctx, "No se pudo cargar la informacion del usuario",Toast.LENGTH_SHORT).show()
            }

        })


    }
}