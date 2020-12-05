package com.example.pia_sismov.presentation.posts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_sismov.R
import com.example.pia_sismov.models.Publicacion
import kotlinx.android.synthetic.main.item_view_publicacion.view.itemImgPostImage
import kotlinx.android.synthetic.main.item_view_publicacion.view.itemTxtPostMessage
import kotlinx.android.synthetic.main.item_view_publicacion.view.itemTxtPostTitle

class MainPostsFragmentAdapter(
        private val list: List<Publicacion>
) :
    RecyclerView.Adapter<MainPostsFragmentAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(post: Publicacion) {

            itemView.itemTxtPostTitle.text = post.title
            itemView.itemTxtPostMessage.text = post.message
            itemView.itemImgPostImage.setImageResource(R.drawable.ic_launcher_background)

            //itemView.setOnClickListener{fragAdmin.launchActivity(98)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_publicacion, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size
}


