package com.example.pia_sismov.presentation.account.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.example.pia_sismov.R
import com.example.pia_sismov.domain.interactors.posts.UploadPP
import com.example.pia_sismov.domain.interactors.register.RegisterUser
import com.example.pia_sismov.presentation.account.IRegisterContract
import com.example.pia_sismov.presentation.account.model.UserRegisterData
import com.example.pia_sismov.presentation.account.presenter.RegisterPresenter
import com.example.pia_sismov.presentation.main.view.MainActivity
import com.example.pia_sismov.repos.PostImageRepository
import fcfm.lmad.poi.ChatPoi.presentation.shared.view.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity<IRegisterContract.IView,RegisterPresenter>(),IRegisterContract.IView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_register.setOnClickListener{
            signUp()
        }
        btn_load_profile_pic.setOnClickListener{
            loadImage()
        }

    }

    override fun getLayout() = R.layout.activity_register
    override fun instantiatePresenter() = RegisterPresenter(RegisterUser(), UploadPP(
        PostImageRepository()
    ))
    override fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun signUp() {
        val name: String = etxt_register_name.text.toString()
        val lastName: String = etxt_register_last_name.text.toString()
        val email: String = etxt_register_email.text.toString()
        val pass1: String = etxt_register_pass1.text.toString()
        val pass2: String = etxt_register_pass2.text.toString()
        val phone: String = etxt_register_phone.text.toString()
        val data = UserRegisterData(name,lastName,email,pass1, pass2,phone,imageUri)
        if(presenter.isUserDataValidForRegistration(data)){
           presenter.register(data)
        }
    }

    private val pickImage = 100
    private var imageUri: Uri? = null

     fun loadImage() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
        }
    }

    override fun showProgressBar() {
        pbar_loading.visibility = View.VISIBLE
        btn_register.visibility = View.GONE
    }

    override fun hideProgressBar() {
        pbar_loading.visibility = View.GONE
        btn_register.visibility = View.VISIBLE
    }
}