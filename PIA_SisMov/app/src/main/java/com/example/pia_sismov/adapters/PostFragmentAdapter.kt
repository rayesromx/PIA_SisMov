package com.example.pia_sismov.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_sismov.IFragManager
import com.example.pia_sismov.R
import com.example.pia_sismov.models.Post
import kotlinx.android.synthetic.main.item_view_post.view.*

class PostFragmentAdapter (
    private val list: List<Post>,
    var fragManager: IFragManager
) : RecyclerView.Adapter<PostFragmentAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(post: Post) {
            itemView.itemTxtPostMessage.text = post.message
            itemView.itemImgPostImage.setImageResource(R.drawable.ic_launcher_background)// = currentFile.time
            itemView.itemTxtPostTitle.text = post.title
            //itemView.itemBtnAction.setOnClickListener{ Toast.makeText("")}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size
}