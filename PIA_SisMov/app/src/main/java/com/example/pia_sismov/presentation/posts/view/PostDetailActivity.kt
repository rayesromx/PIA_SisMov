package com.example.pia_sismov.presentation.posts.view

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pia_sismov.CustomSessionState
import com.example.pia_sismov.DataBaseHandler
import com.example.pia_sismov.R
import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.interactors.posts.CreateNewDocument
import com.example.pia_sismov.domain.interactors.posts.GetAllImagesFromPost
import com.example.pia_sismov.domain.interactors.posts.SavePost
import com.example.pia_sismov.presentation.posts.IPostDetailContract
import com.example.pia_sismov.presentation.posts.adapters.PostDetailImageListAdapter
import com.example.pia_sismov.presentation.posts.model.DtoDocument
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
    var myDownloadId : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DataBaseHandler(this)
        etxt_detail_title.setText(CustomSessionState.currentPost.title)
        etxt_detail_description.setText(CustomSessionState.currentPost.description)
        txt_created_by.setText("Creado por " + CustomSessionState.currentPost.createdByName)

        btn_detail_save.setOnClickListener{
            CustomSessionState.currentPost.title =  etxt_detail_title.text.toString()
            CustomSessionState.currentPost.description =  etxt_detail_description.text.toString()

            if(!CustomSessionState.hayInternet){
                db.insertPost(CustomSessionState.currentPost)
                val posts = db.readpOSTData()
                var postFromDb = Post()
                for (p in posts){
                    if(p.title == CustomSessionState.currentPost.title && p.description == CustomSessionState.currentPost.description){
                        postFromDb = p
                    }
                }
                //guidardar en db las imagenes
                for (img in presenter.imageList){
                    img.postId = postFromDb.uid
                    db.insertImage(img)
                }
                //guardar la referencia del dpocumento
                if(presenter.file!=null) {
                    presenter.file!!.postId = postFromDb.uid
                    db.insertImage(presenter.file!!)
                }
            }
            else
            presenter.onPostSaved(CustomSessionState.currentPost)
        }



        btn_detail_publish.setOnClickListener{
            try {
                val parsedInt = CustomSessionState.currentPost.uid.toInt()
                db.deletePost(CustomSessionState.currentPost)
            } catch (nfe: NumberFormatException) {
                // not a valid int
            }
            CustomSessionState.currentPost.title =  etxt_detail_title.text.toString()
            CustomSessionState.currentPost.description =  etxt_detail_description.text.toString()
            //CustomSessionState.currentPost.uid = ""
            CustomSessionState.currentPost.createdBy = CustomSessionState.currentUser.uid
            CustomSessionState.currentPost.createdByName = CustomSessionState.currentUser.name + " " + CustomSessionState.currentUser.lastName
            presenter.onPostLoaded(CustomSessionState.currentPost)
        }

        if(!CustomSessionState.isEditingPost){
            btn_load_detail_image.visibility = View.GONE
            btn_load_detail_document.visibility = View.GONE
            btn_detail_save.visibility = View.GONE
            btn_detail_publish.visibility = View.GONE
            etxt_detail_title.isEnabled = false
            etxt_detail_description.isEnabled = false

        }else{
            txt_created_by.visibility = View.GONE
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

        if(!CustomSessionState.hayInternet){
            btn_detail_publish.isEnabled = false
            onUpdatedImageRV()
        }
        else {
            presenter.loadImageFromPost(CustomSessionState.currentPost)
        }

        var ctx = this
        var br = object:BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                var id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if(id == myDownloadId){
                    toast(ctx, "Descarga completa")
                }
            }
        }
        registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onImageDeleted(img: DtoDocument) {
        presenter.removeImageFromList(img)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            val img = DtoDocument("img",imageUri!!)
            presenter.addImageToList(img)
        } else if (resultCode == RESULT_OK && requestCode == PDF) {
            imageUri = data?.data
            val img = DtoDocument("doc",imageUri!!)
            presenter.loadFile(img)
        }
    }

    override fun onUpdatedImageRV() {
        var images = db.readImageData(CustomSessionState.currentPost)
        var imagedType = ArrayList<DtoDocument>()
        for(img in images){
            if(img.type == "img"){
                val im = DtoDocument("img",img.uri)
                im.bmpImage = img.bmpImage
                imagedType.add(im)
            }
        }
        presenter.imageList.addAll(imagedType)
       val adapter = PostDetailImageListAdapter(presenter.imageList,this)
        val ll = LinearLayoutManager(this)
        ll.orientation = LinearLayoutManager.HORIZONTAL
        rv_imgv_detail_carrousel.layoutManager = ll
        rv_imgv_detail_carrousel.adapter = adapter

        if(!presenter.file!!.url.isBlank()){
            if(!CustomSessionState.isEditingPost){
                btn_load_detail_document.visibility = View.VISIBLE
                btn_load_detail_document.text = "Descargar " + presenter.file!!.filename
                btn_load_detail_document.setOnClickListener{downloadFile()}
            }else{
                txt_created_by.visibility = View.GONE
            }

        }
    }


    fun downloadFile(){
        try {
            var fn = presenter.file!!.filename.replace(":","_")
            var request  = DownloadManager.Request(
                Uri.parse(presenter.file!!.url))
                .setTitle(presenter.file!!.filename)
                //.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setAllowedOverMetered(true)
                // .setDescription("Descargando...")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fn)

            var dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            myDownloadId = dm.enqueue(request)
        }catch(ex:Exception){
            toast(this,ex.message!!)
        }

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