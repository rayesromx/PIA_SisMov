package com.example.pia_sismov.presentation.posts.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_sismov.R
import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.presentation.posts.IMainDraftsFragmentContract
import kotlinx.android.synthetic.main.activity_post_detail.view.*
import kotlinx.android.synthetic.main.item_view_mi_publicacion.view.*
import kotlinx.android.synthetic.main.item_view_post.view.*
import kotlinx.android.synthetic.main.item_view_publicacion.view.itemImgPostImage
import kotlinx.android.synthetic.main.item_view_publicacion.view.itemTxtPostMessage
import kotlinx.android.synthetic.main.item_view_publicacion.view.itemTxtPostTitle

class DraftPostFragmentAdapter(
    private val list: List<Post>,
    private val parentView: IMainDraftsFragmentContract.IView
) :
        RecyclerView.Adapter<DraftPostFragmentAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(post: Post) {
            itemView.itemTxtPostTitle.text = post.title
            itemView.itemTxtPostMessage.text = post.description
            itemView.itemImgPostImage.setImageResource(R.drawable.ic_launcher_background)
            itemView.itemBtnAction.setText("Editar")
            itemView.itemBtnAction.setOnClickListener{parentView.onPostSelected(post)}
            itemView.setOnClickListener{parentView.onViewUser(post.createdBy)}

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_view_mi_publicacion, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size
}