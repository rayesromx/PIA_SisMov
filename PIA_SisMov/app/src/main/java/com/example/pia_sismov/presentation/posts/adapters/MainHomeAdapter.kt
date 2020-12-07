package com.example.pia_sismov.presentation.posts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_sismov.R
import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.presentation.posts.IMainHomeFragmentContract
import kotlinx.android.synthetic.main.item_view_publicacion.view.*

class MainHomeAdapter(
    private val list: List<Post>,
    private val parentView: IMainHomeFragmentContract.IView
) :
        RecyclerView.Adapter<MainHomeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(post: Post) {
            itemView.itemTxtPostTitle.text = post.title
            itemView.itemTxtPostMessage.text = post.description
            itemView.itemImgPostImage.setImageResource(R.drawable.ic_launcher_background)
            itemView.setOnClickListener{parentView.onPostSelected(post)}
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