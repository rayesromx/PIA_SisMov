package com.example.pia_sismov.presentation.posts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_sismov.CustomSessionState
import com.example.pia_sismov.R
import com.example.pia_sismov.presentation.posts.IPostDetailContract
import com.example.pia_sismov.presentation.posts.model.DtoDocument
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_carrousel_item_editable.view.*

class PostDetailImageListAdapter(
    val imageList: List<DtoDocument>,
    val parentView: IPostDetailContract.IView
)  : RecyclerView.Adapter<PostDetailImageListAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(img: DtoDocument) {
            if(img.url.isEmpty()){

               itemView.image_picture.setImageBitmap( img.bmpImage)
            }else
                Picasso.get().load(img.url).into(itemView.image_picture)

            if(CustomSessionState.isEditingPost){
                itemView.btn_delete_image_from_list.setOnClickListener{
                    parentView.onImageDeleted(img)
                }
            }
            else{
                itemView.btn_delete_image_from_list.visibility = View.GONE
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.image_carrousel_item_editable, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(imageList[position])
    }

    override fun getItemCount() = imageList.size
}