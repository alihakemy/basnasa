package com.market.presentation.mainscreen.user.displayproduct.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.market.data.models.get.productdetails.Rate
import com.market.databinding.CommentsItemBinding
import com.market.databinding.SearchItemUserBinding
import com.market.presentation.mainscreen.user.search.adapter.SearchAdapter

class CommentsAdapter(private val rates: List<Rate>) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {
    inner class CommentsViewHolder(val binding: CommentsItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(get: Rate) {

            binding.CommentContent.text=get.comment.toString()
            get.rate?.let {
                binding.ratingBar.rating= it.toFloat()
            }
            binding.textView49.text=get?.name.toString()
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

    override fun onBindViewHolder(holder:CommentsAdapter.CommentsViewHolder, position: Int) {

        holder.bind(rates.get(position))

    }

    override fun getItemCount(): Int {

        return  rates.size
    }
}