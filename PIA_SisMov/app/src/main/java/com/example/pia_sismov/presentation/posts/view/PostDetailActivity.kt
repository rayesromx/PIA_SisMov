package com.example.pia_sismov.presentation.posts.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pia_sismov.CustomSessionState
import com.example.pia_sismov.DataBaseHandler
import com.example.pia_sismov.R
import com.example.pia_sismov.domain.interactors.posts.CreateNewDocument
import com.example.pia_sismov.domain.interactors.posts.GetAllImagesFromPost
import com.example.pia_sismov.domain.interactors.posts.SavePost
import com.example.pia_sismov.presentation.posts.IPostDetailContract
import com.example.pia_sismov.presentation.posts.adapters.PostDetailImageListAdapter
import com.example.pia_sismov.presentation.posts.model.EditableImage
import com.example.pia_sismov.presentation.posts.presenter.PostDetailPresenter
import com.example.pia_sismov.repos.PostImageRepository
import com.example.pia_sismov.repos.PostRepository
import fcfm.lmad.poi.ChatPoi.presentation.shared.view.BaseActivity
import kotlinx.android.synthetic.main.activity_post_detail.*

class PostDetailActivity :
    BaseActivity<IPostDetailContract.IView, PostDetailPresenter>(), IPostDetailContract.IView {

    private val pickImage = 100
    private val PDF = 200
    private var imageUri: Uri? = null
    lateinit var db: DataBaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DataBaseHandler(this)
        etxt_detail_title.setText(CustomSessionState.currentPost.title)
        etxt_detail_description.setText(CustomSessionState.currentPost.description)
        txt_created_by.setText("Creado por " + CustomSessionState.currentPost.createdByName)

        btn_detail_save.setOnClickListener{presenter.onPostSaved(CustomSessionState.currentPost)}
        btn_detail_publish.setOnClickListener{
            try {
                val parsedInt = CustomSessionState.currentPost.uid.toInt()
                db.deletePost(CustomSessionState.currentPost)
            } catch (nfe: NumberFormatException) {
                // not a valid int
            }
            CustomSessionState.currentPost.uid = ""
            CustomSessionState.currentPost.createdBy = CustomSessionState.currentUser.uid
            presenter.onPostLoaded(CustomSessionState.currentPost)
        }

        if(!CustomSessionState.isEditingPost){
            btn_load_detail_image.visibility = View.GONE
            btn_load_detail_document.visibility = View.GONE
            btn_detail_save.visibility = View.GONE
            btn_detail_publish.visibility = View.GONE
        }else{
            txt_created_by.visibility = View.GONE
        }

        if(!CustomSessionState.hayInteret){

            btn_load_detail_image.isEnabled = false
            btn_load_detail_document.isEnabled = false
            btn_detail_publish.isEnabled = false
        }

        btn_load_detail_image.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        btn_load_detail_document.setOnClickListener{
            val intent = Intent()
            intent.setType ("*/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Selecciona un document"), PDF)
        }

        presenter.loadImageFromPost(CustomSessionState.currentPost)
    }

    override fun onImageDeleted(img: EditableImage) {
        presenter.removeImageFromList(img)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            val img = EditableImage("img",imageUri!!)
            presenter.addImageToList(img)
        } else if (resultCode == RESULT_OK && requestCode == PDF) {
            imageUri = data?.data
            val img = EditableImage("doc",imageUri!!)
            presenter.loadFile(img)
        }
    }

    override fun onUpdatedImageRV() {
       val adapter = PostDetailImageListAdapter(presenter.imageList,this)
        val ll = LinearLayoutManager(this)
        ll.orientation = LinearLayoutManager.HORIZONTAL
        rv_imgv_detail_carrousel.layoutManager = ll
        rv_imgv_detail_carrousel.adapter = adapter
    }

    override fun finishFrag() {
        this.onBackPressed()
    }

    override fun getLayout() = R.layout.activity_post_detail
    override fun instantiatePresenter() = PostDetailPresenter (
        GetAllImagesFromPost(PostImageRepository()),
        SavePost(PostRepository(),
            PostImageRepository()),
        CreateNewDocument(PostImageRepository())

    )
}