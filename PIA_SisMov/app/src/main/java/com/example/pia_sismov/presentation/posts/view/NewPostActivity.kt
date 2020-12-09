package com.example.pia_sismov.presentation.posts.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pia_sismov.CustomSessionState
import com.example.pia_sismov.DataBaseHandler
import com.example.pia_sismov.R
import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.interactors.posts.CreateNewDocument
import com.example.pia_sismov.domain.interactors.posts.CreateNewPost
import com.example.pia_sismov.presentation.posts.INewPostContract
import com.example.pia_sismov.presentation.posts.adapters.NewPostImageListAdapter
import com.example.pia_sismov.presentation.posts.model.DtoDocument
import com.example.pia_sismov.presentation.posts.model.NewPost
import com.example.pia_sismov.presentation.posts.presenter.NewPostPresenter
import com.example.pia_sismov.repos.PostImageRepository
import com.example.pia_sismov.repos.PostRepository
import fcfm.lmad.poi.ChatPoi.presentation.shared.view.BaseActivity
import kotlinx.android.synthetic.main.activity_new_post.*
import java.io.File
import java.time.LocalDateTime

class NewPostActivity :
    BaseActivity<INewPostContract.IView, NewPostPresenter>(),INewPostContract.IView {

    private val pickImage = 100
    private val PDF = 200
    private var imageUri: Uri? = null
    lateinit var db: DataBaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_load_image.setOnClickListener{loadImage()}
        btn_load_document.setOnClickListener{loadFile()}
        btn_publish.setOnClickListener{publishPost()}
        btn_save.setOnClickListener{publishDraft()}

        if(!CustomSessionState.hayInternet){
            db = DataBaseHandler(this)
            btn_publish.isEnabled = false
            //btn_load_image.isEnabled = false
            //btn_load_document.isEnabled = false
        }


    }

    override fun getLayout() = R.layout.activity_new_post

    override fun instantiatePresenter() = NewPostPresenter(
        CreateNewPost(PostRepository()),
        CreateNewDocument(PostImageRepository())
    )

    override fun loadImage() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)
    }

    override fun loadFile() {
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Selecciona archivo"), PDF)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            val img = DtoDocument("img",imageUri!!)
            val f = File(imageUri!!.path!!)
            img.filename = f.name
            img.extension = f.extension
            presenter.addImageToList(img)

        } else if (resultCode == RESULT_OK && requestCode == PDF) {
            imageUri = data?.data
            val doc = DtoDocument("doc",imageUri!!)
            val f = File(imageUri!!.path!!)
            doc.filename = f.name
            doc.extension = f.extension
            presenter.loadFile(doc)

        }
    }

    override fun onImageDeleted(img: DtoDocument){
        presenter.removeImageFromList(img)
    }

    override fun onUpdatedImageRV(){
        val adapter = NewPostImageListAdapter(presenter.imageList,this)
        val ll = LinearLayoutManager(this)
        ll.orientation = LinearLayoutManager.HORIZONTAL
        rv_imgv_carrousel.layoutManager = ll
        rv_imgv_carrousel.adapter = adapter
    }

    override fun publishPost(){

        var post = NewPost(
            CustomSessionState.currentUser.uid,
            etxt_title.text.toString(),
            etxt_description.text.toString(),
            presenter.imageList,
            presenter.file!!,
            LocalDateTime.now().toString()
        )
        presenter.publish(post)
    }

    override fun publishDraft(){
        var post = NewPost(
            CustomSessionState.currentUser.uid,
            etxt_title.text.toString(),
            etxt_description.text.toString(),
            presenter.imageList,
            presenter.file!!,
            ""
        )

        if(!CustomSessionState.hayInternet){
            var p = Post()
            p.description = post.description
            p.title = post.title
            p.uid = "local"
            val postnewid = db.insertPost(p)
            //val posts = db.readpOSTData()

            //guidardar en db las imagenes
            for (img in presenter.imageList){
                img.postId = postnewid.toString()
                db.insertImage(img)
            }
            //guardar la referencia del dpocumento
            if(presenter.file!=null) {
                presenter.file!!.postId = postnewid.toString()
                db.insertImage(presenter.file!!)
            }
            finishFrag()
        }
        else
        presenter.publish(post)
    }

    override fun finishFrag() {
        finish()
    }
}