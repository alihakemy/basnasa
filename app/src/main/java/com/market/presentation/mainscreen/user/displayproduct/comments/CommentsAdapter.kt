package com.market.presentation.mainscreen.user.displayproduct.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.market.data.models.get.productdetails.Rate
import com.market.databinding.CommentsItemBinding
import com.market.databinding.SearchItemUserBinding
import com.market.presentation.mainscreen.user.search.adapter.SearchAdapter

class CommentsAdapter(
    private val rates: ArrayList<Rate>, val userId: Int,
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

            binding.imageView29.isVisible = get.user_id.toString().equals(userId.toString())
            binding.imageView30.isVisible = get.user_id.toString().equals(userId.toString())
            if (get.user_id.toString().equals(userId.toString())) {
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

        holder.bind(rates.get(position))

    }

    override fun getItemCount(): Int {

        return rates.size
    }
}