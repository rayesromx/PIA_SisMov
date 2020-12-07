package com.example.pia_sismov.presentation.posts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_sismov.R
import com.example.pia_sismov.presentation.posts.INewPostContract
import com.example.pia_sismov.presentation.posts.model.EditableImage
import kotlinx.android.synthetic.main.image_carrousel_item_editable.view.*

class NewPostImageListAdapter(
    val imageList: List<EditableImage>,
    val parentView: INewPostContract.IView
)  : RecyclerView.Adapter<NewPostImageListAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(img: EditableImage) {
            itemView.image_picture.setImageURI(img.uri)
            itemView.btn_delete_image_from_list.setOnClickListener{
                parentView.onImageDeleted(img)
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