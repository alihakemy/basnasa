package com.market.presentation.mainscreen.user.displayproduct.comments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.market.data.models.get.productdetails.Rate
import com.market.databinding.CommentsItemBinding

class CommentsAdapter(
    private val rates: ArrayList<Rate>, val userId: Int, val i: Boolean,
    inline val deleteComment: (get: Rate) -> Unit,
    inline val editComment: (get: Rate) -> Unit
) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {
    inner class CommentsViewHolder(val binding: CommentsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(get: Rate, position: Int) {

            binding.CommentContent.text = get.comment.toString()
            get.rate?.let {
                binding.ratingBar.rating = it.toFloat()
            }
            binding.textView49.text = get?.name.toString()

            binding.textView54.text = get?.createdAt.toString()
            binding.imageView29.isVisible = get.userId.toString().equals(userId.toString())
            binding.imageView30.isVisible = get.userId.toString().equals(userId.toString())
            if (get.userId.toString().equals(userId.toString())) {
                // delete
                binding.imageView29.setOnClickListener {

                    Log.e("PostionALI", position.toString())
                    Log.e("PostionALI2", rates.size.toString())
                    deleteComment(get)
                    rates.removeAt(position)
                    notifyItemRemoved(position)

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


            holder.bind(rates[position], position)

    }

    override fun getItemCount(): Int {

        return rates.size


    }
}