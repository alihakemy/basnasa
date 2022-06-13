package com.market.presentation.mainscreen.user.displayproduct

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.market.data.models.get.productdetails.ProductDetails
import com.market.data.models.get.productdetails.Rate
import com.market.databinding.ActivityDisplayProductBinding
import com.market.presentation.mainscreen.user.displayproduct.comments.CommentsAdapter
import com.market.presentation.mainscreen.user.search.adapter.SearchAdapter
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisplayProduct : AppCompatActivity() {
    lateinit var binding:ActivityDisplayProductBinding

    private val viewModel:DisplayProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDisplayProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getProductDetails(1.toString())

//        if(!intent.hasExtra("productId")){
//            return
//        }

        intent.getStringExtra("productId")?.let { viewModel.getProductDetails(it) }
        viewModel.results.observe(this, Observer {

            when(val results =it){
                is ResultState.Success<ProductDetails> ->{

                    renderProduct(results.data)


                }
                else ->{

                }
            }

        })
    }
    private fun renderProduct(data: ProductDetails?) {
        Glide.with(this).load(
            data?.data?.products?.image_path
        ).into(binding.productImage)

        binding.productName.text= data?.data?.products?.name.toString()
        binding.textView44.text= data?.data?.products?.tager.toString()
        Glide.with(this).load(
            data?.data?.products?.tager_image
        ).into(binding.TagerImage)


        binding.textView45.text= data?.data?.products?.discount?.toString() +"Off"

        binding.textView32.text= data?.data?.products?.mainprice?.toString()

        binding.textView33.text= data?.data?.products?.prefitPrice?.toInt().toString()

        binding.productDetails.text=data?.data?.products?.content.toString()
        data?.data?.products?.rate?.let {
            binding.ratingBar.rating=it.toFloat()

        }
        binding.textView20.text="("+data?.data?.rates?.size +")"

        data?.data?.rates?.let { renderComments(it) }

    }

    fun renderComments(rates: List<Rate>) {
        binding.CommentsRec.layoutManager = LinearLayoutManager(this)
        binding.CommentsRec.adapter = CommentsAdapter(rates)
    }
    companion object{
        fun startDisplayProduct(productId:String,context: Context){
            val intent = Intent(context,DisplayProduct::class.java)
            intent.putExtra("productId",productId)
            context.startActivity(intent)
        }
    }
}