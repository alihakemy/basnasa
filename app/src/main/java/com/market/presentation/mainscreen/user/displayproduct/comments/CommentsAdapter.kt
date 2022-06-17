package com.market.presentation.mainscreen.user.displayproduct.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.market.data.models.get.productdetails.Rate
import com.market.databinding.CommentsItemBinding

class CommentsAdapter(
    private val rates: ArrayList<Rate>, val userId: Int,val i: Int,
    inline val deleteComment: (get: Rate) -> Unit,
    inline val editComment: (get: Rate) -> Unit
) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {
    inner class CommentsViewHolder(val binding: CommentsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(get: Rate) {

            binding.CommentContent.text = get.comment.toString()
            get.rate?.let {
                binding.ratingBar.rating = it.toFloat()
            }
            binding.textView49.text = get?.name.toString()

            binding.textView54.text=get?.createdAt.toString()
            binding.imageView29.isVisible = get.userId.toString().equals(userId.toString())
            binding.imageView30.isVisible = get.userId.toString().equals(userId.toString())
            if (get.userId.toString().equals(userId.toString())) {
                // delete
                binding.imageView29.setOnClickListener {

                    rates.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    deleteComment(get)
                }

                // edit Comment
                binding.imageView30.setOnClickListener {

                    editComment(get)
                }

            }

        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentsViewHolder {
        return CommentsViewHolder(
            CommentsItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentsAdapter.CommentsViewHolder, position: Int) {

        holder.bind(rates[position])

    }

    override fun getItemCount(): Int {

        return i
    }
}