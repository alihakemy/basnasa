package com.market.presentation.mainscreen.trader.showMyProfile

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.like.LikeButton
import com.like.OnLikeListener
import com.market.R
import com.market.data.models.get.productdetails.Rate
import com.market.data.models.get.tagerdetails.Category
import com.market.data.models.get.tagerdetails.Data
import com.market.data.models.get.tagerdetails.TagerDetails
import com.market.databinding.ActivityShowMyTagerProfileBinding
import com.market.databinding.ActivityTraderProfileBinding
import com.market.presentation.bases.BaseActivity
import com.market.presentation.mainscreen.user.displayproduct.comments.CommentsAdapter
import com.market.presentation.mainscreen.user.displaytrader.TagerDetailsViewModel
import com.market.presentation.mainscreen.user.displaytrader.TraderProfileActivity
import com.market.presentation.mainscreen.user.displaytrader.catadapter.CategoriesAdapter
import com.market.presentation.mainscreen.user.displaytrader.moretager.MoreTagerActivity
import com.market.presentation.mainscreen.user.displaytrader.tageradapter.ProductAdapter
import com.market.utils.ResultState
import com.market.utils.startLink
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowMyTagerProfile : BaseActivity() {
    val viewModel: TagerDetailsViewModel by viewModels()

    lateinit var binding: ActivityShowMyTagerProfileBinding
    var edit = false
    var CommentId = 0
    var showAll = false
    lateinit var pd: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityShowMyTagerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pd = ProgressDialog(this)
        pd.setMessage("loading")
        pd.setCancelable(false)
        pd.show()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                kotlin.runCatching {
                    pd.show()
                }
                viewModel.getProductDetails(intent.getStringExtra("tagerId").toString(), getLatLong().first, getLatLong().second)

            }
        }

        viewModel.results.observe(this, androidx.lifecycle.Observer {
            if (pd.isShowing) {
                pd.dismiss()
            }
            when (val results = it) {
                is ResultState.Success<TagerDetails> -> {

                    renderData(results.data?.data)

                }
                else -> {

                }
            }

        })
    }

    private fun renderData(data: Data?) {


        binding.imageView38.setOnClickListener {
            data?.merchant?.snapchatLink?.let { it1 -> startLink(it1,this) }
        }

        binding.imageView40.setOnClickListener {
            data?.merchant?.facebookLink?.let { it1 -> startLink(it1,this) }
        }
        binding.imageView41.setOnClickListener {
            data?.merchant?.instagramLink?.let { it1 -> startLink(it1,this) }
        }
        binding.imageView38.setOnClickListener {
            data?.merchant?.whatsappLink?.let { it1 -> startLink(it1,this) }
        }

        binding.imageView44.setOnClickListener {
            data?.merchant?.whatsappLink?.let { it1 -> startLink(it1,this) }

        }









        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        val cat = data?.categories



        if (cat?.size ?: 0 > 0) {
            cat?.get(0)?.selected = true
        }
//        cat?.add(Category("","",10,"","","sa","",false))


        binding.RecCat.adapter = CategoriesAdapter(cat){
            callDependOnCat(it)

        }
        binding.RecCat.layoutManager = linearLayoutManager



        val linearLayoutManagerProduct = GridLayoutManager(this,2)
        binding.rec.adapter = ProductAdapter(data?.products)
        binding.rec.layoutManager =   linearLayoutManagerProduct



        binding.tagerName.text = data?.merchant?.name.toString()
        Glide.with(this).load(data?.merchant?.imagePath.toString()).into(binding.circleImageView)

        binding.textView56.text = data?.merchant?.name.toString()

        binding.textView58.text = data?.merchant?.textOrder.toString()
        binding.text.text = data?.merchant?.textOrder.toString()
        binding.textView26.text = data?.merchant?.distance.toString()
        kotlin.runCatching {
            binding.ratingBar.rating = (data?.merchant?.rate ?: 0f) as Float
        }
        binding.textView.text = "(" + data?.merchant?.rateCount + ")"
        binding.textView57.text = data?.merchant?.discountText

        Glide.with(this).load(data?.merchant?.bannerPath.toString()).into(binding.banner)


        binding.back.setOnClickListener {
            onBackPressed()
        }

        if (!showAll) {
            if (data?.rates?.size ?: 0 > 0) {
                val list = ArrayList<Rate>()
                data?.rates?.let { it1 -> list.add(it1.first()) }
                renderComments(list, false)

            }
        } else {
            data?.rates?.let { renderComments(it, true) }

        }
        binding.textView47.setOnClickListener {
            if (!showAll) {
                showAll = true

                binding.textView47.text = "اخفاء"


                data?.rates?.let { renderComments(it, true) }

            } else {
                showAll = false
                binding.textView47.text = "عرض الكل"


                if (data?.rates?.size ?: 0 > 0) {
                    val list = ArrayList<Rate>()
                    data?.rates?.let { it1 -> list.add(it1.first()) }
                    renderComments(list, false)

                }


            }

        }


    }

    private fun callDependOnCat(cat_id: Category?){

        viewModel.getProductDetails(cat_id?.id.toString(),
            intent.getStringExtra("tagerId").toString(), getLatLong().first, getLatLong().second)


    }

    private fun renderComments(rates: List<Rate>, i: Boolean) {
        binding.CommentsRec.layoutManager = LinearLayoutManager(this)
        binding.CommentsRec.adapter = CommentsAdapter(rates as ArrayList<Rate>,
            getLoginData().data.user.id, i, {


            }) {



        }
    }

    companion object {
        fun startTagerProfile(productId: String, context: Context) {
            val intent = Intent(context, ShowMyTagerProfile::class.java)
            intent.putExtra("tagerId", productId)
            context.startActivity(intent)
        }
    }

}