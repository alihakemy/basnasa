package com.market.presentation.mainscreen.user.displayproduct

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.market.data.models.get.productdetails.Image
import com.market.data.models.get.productdetails.ProductDetails
import com.market.data.models.get.productdetails.Rate
import com.market.databinding.ActivityDisplayProductBinding
import com.market.presentation.bases.BaseActivity
import com.market.presentation.mainscreen.user.displayproduct.comments.CommentsAdapter
import com.market.presentation.mainscreen.user.displayproduct.pagers.ProductImageFragment
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.ceil


@AndroidEntryPoint
class DisplayProduct : BaseActivity() {
    lateinit var binding: ActivityDisplayProductBinding

    private val viewModel: DisplayProductViewModel by viewModels()
    lateinit var pd: ProgressDialog
    var edit = false
    var CommentId = 0
    var showAll = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pd = ProgressDialog(this)
        pd.setMessage("loading")

        pd.setCancelable(false)

        if (!intent.hasExtra("productId")) {
            return
        }

        val productId: String = intent.getStringExtra("productId").toString()
        intent.getStringExtra("productId")?.let {
            viewModel.getProductDetails(
                it,
                getLatLong().first, getLatLong().second
            )
        }
        viewModel.results.observe(this, Observer {
            if (pd.isShowing) {
                pd.dismiss()
            }
            when (val results = it) {
                is ResultState.Success<ProductDetails> -> {

                    renderProduct(results.data)


                }
                else -> {

                }
            }

        })

        binding.ratingBar2.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                if (fromUser) {
                    ratingBar.rating = ceil(rating.toDouble()).toFloat()
                }
            }
        binding.ratingBar2.setOnClickListener {
            Log.e("Clicked", "aslkdl;s")
        }


        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.button.setOnClickListener {

            if (!edit) {


                if (!binding.CommentText.text.toString().isNullOrEmpty()) {
                    pd.show()
                    viewModel.addComment(
                        getLoginData().data.token,
                        productId,
                        binding.ratingBar2.rating,
                        binding.CommentText.text.toString(),
                        getLatLong().first,
                        getLatLong().second
                    )
                    binding.CommentText.setText("")
                    binding.ratingBar2.rating = 0.0f

                } else {
                    Toast.makeText(this, "اضف التعليق ", Toast.LENGTH_LONG).show()
                }
            } else {
                edit = false
                if (!binding.CommentText.text.toString().isNullOrEmpty()) {
                    binding.textView50.visibility = View.GONE

                    pd.show()
                    viewModel.editeComment(
                        getLoginData().data.token,
                        productId, binding.ratingBar2.rating, binding.CommentText.text.toString(),
                        CommentId, getLatLong().first, getLatLong().second
                    )
                    binding.CommentText.setText("")
                    binding.ratingBar2.rating = 0.0f

                } else {
                    Toast.makeText(this, "اضف التعليق ", Toast.LENGTH_LONG).show()
                }
            }


        }


    }

    private fun renderProduct(data: ProductDetails?) {

        binding.productName.text = data?.data?.products?.name.toString()
        binding.textView44.text = data?.data?.products?.tager.toString()
        Glide.with(this).load(
            data?.data?.products?.tagerImage
        ).into(binding.TagerImage)

        binding.imageView39.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=" + data?.data?.products?.tagerPhone
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        val listImages = data?.data?.products?.images?.add(Image(data?.data?.products?.imagePath))
        binding.banner.adapter = ScreenSlidePagerAdapter(
            this, data?.data?.products?.images

        )

        TabLayoutMediator(binding.tabLayout, binding.banner) { tab, position ->
        }.attach()

        binding.textView45.text = data?.data?.products?.discount?.toString() + "%Off"

        binding.textView32.text = data?.data?.products?.prefitPrice?.toString()

        if (data?.data?.products?.mainprice?.toString()?.toDouble() == 0.0) {
            binding.textView33.visibility = View.GONE
        } else {
            binding.textView33.visibility = View.VISIBLE
        }
        binding.textView33.text = data?.data?.products?.mainprice

        binding.textView33.paintFlags =
            binding.textView33.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        binding.productDetails.text = data?.data?.products?.content.toString()
        data?.data?.products?.rate?.let {
            binding.ratingBar.rating = it.toFloat()

        }
        binding.textView20.text = "(" + data?.data?.rates?.size + ")"


        if (!showAll) {
            if (data?.data?.rates?.size ?: 0 > 0) {
                val list = ArrayList<Rate>()
                data?.data?.rates?.let { it1 -> list.add(it1.first()) }
                renderComments(list, false)

            }
        } else {
            data?.data?.rates?.let { renderComments(it, true) }

        }
        binding.textView47.setOnClickListener {
            if (!showAll) {
                showAll = true

                binding.textView47.text = "اخفاء"


                data?.data?.rates?.let { renderComments(it, true) }

            } else {
                showAll = false
                binding.textView47.text = "عرض الكل"


                if (data?.data?.rates?.size ?: 0 > 0) {
                    val list = ArrayList<Rate>()
                    data?.data?.rates?.let { it1 -> list.add(it1.first()) }
                    renderComments(list, false)

                }


            }

        }

    }

    private fun renderComments(rates: List<Rate>, i: Boolean) {
        binding.CommentsRec.layoutManager = LinearLayoutManager(this)
        binding.CommentsRec.adapter = CommentsAdapter(rates as ArrayList<Rate>,
            getLoginData().data.user.id, i, {
                deleteComment(it)

            }) {

            editComment(it)

        }
    }

    private fun editComment(rate: Rate) {
        CommentId = rate.id ?: 0
        binding.CommentText.setText(rate.comment.toString())
        binding.ratingBar2.rating = rate.rate?.toFloat() ?: 0f

        edit = true
        binding.textView50.visibility = View.VISIBLE
        binding.textView50.setOnClickListener {
            edit = false
            binding.textView50.visibility = View.GONE
            binding.CommentText.setText("")
            binding.ratingBar2.rating = 0.0f
        }


    }

    private fun deleteComment(rate: Rate) {
        viewModel.deleteComment(getLoginData().data.token, rate.id.toString())

    }

    companion object {
        fun startDisplayProduct(productId: String, context: Context) {
            val intent = Intent(context, DisplayProduct::class.java)
            intent.putExtra("productId", productId)
            context.startActivity(intent)
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity, val images: List<Image>?) :
        FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = images?.size ?: 0
        override fun createFragment(position: Int): Fragment =
            ProductImageFragment.newInstance(
                images?.get(position)?.imagePath.toString(), ""
            )
    }
}