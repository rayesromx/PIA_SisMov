package com.example.pia_sismov.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_sismov.IFragManager
import com.example.pia_sismov.MainActivity
import com.example.pia_sismov.R
import com.example.pia_sismov.models.Post
import kotlinx.android.synthetic.main.item_view_post.view.*

class MainPostsFragmentAdapter(
    private val list: List<Post>,
    var ctx: Context,
    var fragManager: IFragManager
) :
    RecyclerView.Adapter<MainPostsFragmentAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(post: Post ) {
            itemView.itemTxtPostTitle.text = post.title
            itemView.itemImgPostImage.setImageResource(R.drawable.ic_launcher_background)
            itemView.itemTxtPostMessage.text = post.message

            itemView.setOnClickListener{launchPostDetailActivity()}
        }
    }

    fun launchPostDetailActivity()
    {
        var intent = Intent(ctx,MainActivity::class.java)
        fragManager.launchActivity(intent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_post, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size
}