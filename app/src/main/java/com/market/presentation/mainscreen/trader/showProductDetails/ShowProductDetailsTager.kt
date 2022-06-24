package com.market.presentation.mainscreen.trader.showProductDetails

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.market.data.models.get.productdetails.Image
import com.market.data.models.get.productdetails.ProductDetails
import com.market.data.models.get.productdetails.Products
import com.market.data.models.get.productdetails.Rate
import com.market.databinding.ActivityShowPostDetailsTagerBinding
import com.market.presentation.bases.BaseActivity
import com.market.presentation.mainscreen.trader.showProductDetails.editeProduct.EditeProduct
import com.market.presentation.mainscreen.user.displayproduct.comments.CommentsAdapter
import com.market.presentation.mainscreen.user.displayproduct.pagers.ProductImageFragment
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowProductDetailsTager : BaseActivity() {
    lateinit var binding: ActivityShowPostDetailsTagerBinding

    private val viewModel: DisplayProductViewModelTager by viewModels()
    lateinit var pd: ProgressDialog

    var showAll = false
    lateinit var product:Products
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPostDetailsTagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.delete.isVisible = false
        pd = ProgressDialog(this)
        pd.setMessage("loading")

        pd.setCancelable(false)

        if (!intent.hasExtra("productId")) {
            return
        }
        pd.show()
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
                    product= results.data?.data?.products!!
                    renderProduct(results.data)


                }
                else -> {

                }
            }

        })




        binding.back.setOnClickListener {
            onBackPressed()
        }

    }

    private fun renderProduct(data: ProductDetails?) {


        binding.productName.text = data?.data?.products?.name.toString()
        binding.textView44.text = data?.data?.products?.tager.toString()
        Glide.with(this).load(
            data?.data?.products?.tagerImage
        ).into(binding.TagerImage)


        val listImages = data?.data?.products?.images?.add(Image(data?.data?.products?.imagePath))
        binding.banner.adapter = ScreenSlidePagerAdapter(
            this, data?.data?.products?.images

        )

        TabLayoutMediator(binding.tabLayout, binding.banner) { tab, position ->
        }.attach()

        binding.textView45.text = data?.data?.products?.discount?.toString() + "%Off"

        binding.textView32.text = data?.data?.products?.prefitPrice?.toString()

        kotlin.runCatching {
            if (data?.data?.products?.mainprice?.toString()?.toDouble() == 0.0) {
                binding.textView33.visibility = View.GONE
            } else {
                binding.textView33.visibility = View.VISIBLE
            }
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

        binding.imageView75.setOnClickListener {
            binding.delete.isVisible = true

        }

        binding.confirm.setOnClickListener {
            if (!pd.isShowing) {
                pd.show()

            }
            viewModel.removeProduct(intent.getStringExtra("productId").toString())

        }
        binding.cancelAction.setOnClickListener {
            binding.delete.isVisible = false
        }

        viewModel.resultsRemove.observe(this, Observer {
            binding.textView94.text = "تم حذف المنتج بنجاح"
            binding.cancelAction.isVisible = false
            binding.confirm.isVisible = false

            if (pd.isShowing) {
                pd.dismiss()
            }
            finish()


        })
        binding.imageView74.setOnClickListener {
            EditeProduct.startEditProduct( product,this)

        }


    }

    private fun renderComments(rates: List<Rate>, i: Boolean) {
        binding.CommentsRec.layoutManager = LinearLayoutManager(this)
        binding.CommentsRec.adapter = CommentsAdapter(rates as ArrayList<Rate>,
            getLoginData().data.user.id, i, {


            }) {


        }


    }


    companion object {
        fun startDisplayProduct(productId: String, context: Context) {
            val intent = Intent(context, ShowProductDetailsTager::class.java)
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