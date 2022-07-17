package com.market.presentation.mainscreen.user.displaytrader

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.like.LikeButton
import com.like.OnLikeListener
import com.market.data.models.get.productdetails.Rate
import com.market.data.models.get.tagerdetails.Category
import com.market.data.models.get.tagerdetails.Data
import com.market.data.models.get.tagerdetails.Product
import com.market.data.models.get.tagerdetails.TagerDetails
import com.market.databinding.ActivityTraderProfileBinding
import com.market.presentation.authentication.user.login.LoginUser
import com.market.presentation.bases.BaseActivity
import com.market.presentation.mainscreen.user.displayproduct.comments.CommentsAdapter
import com.market.presentation.mainscreen.user.displaytrader.catadapter.CategoriesAdapter
import com.market.presentation.mainscreen.user.displaytrader.moretager.MoreTagerActivity
import com.market.presentation.mainscreen.user.displaytrader.tageradapter.ProductAdapter
import com.market.utils.ResultState
import com.market.utils.startLink
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TraderProfileActivity : BaseActivity() {

    val viewModel: TagerDetailsViewModel by viewModels()

    lateinit var binding: ActivityTraderProfileBinding
    var edit = false
    var CommentId = 0
    var showAll = false
    lateinit var pd: ProgressDialog

    var productAdapter: ProductAdapter?=null
    var  flag =false
    fun SharePostLink(id: String)
    {

        val builder = Uri.Builder()
        builder.scheme("https")
            .authority("bsnas.page.link")
            .appendQueryParameter("screenname", "tager")
            .appendQueryParameter("tagerId", id)

        val myUrl = builder.build().toString()


       FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse(myUrl))
            .setDomainUriPrefix("https://bsnas.page.link/") // Open links with this app on Android
            .setAndroidParameters(
                DynamicLink.AndroidParameters.Builder("com.market")
                .build())

            .buildShortDynamicLink().addOnSuccessListener {

                try {

                    val appPackageName = applicationContext.packageName
                    val sendIntent = Intent()
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.putExtra(
                        Intent.EXTRA_TEXT, it.shortLink.toString()
                    )

                    sendIntent.type = "text/plain"
                    sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    applicationContext.startActivity(sendIntent)

                }catch (e:Exception)
                {

                    e.stackTrace
                }


            }




    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTraderProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pd = ProgressDialog(this)
        pd.setMessage("loading")
        pd.setCancelable(false)
        pd.show()


        Log.e("TagerIDDD",intent.extras?.getString("tagerId").toString())
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                kotlin.runCatching {
                    pd.show()
                }
                viewModel.getProductDetails(
                    intent.extras?.getString("tagerId").toString(),
                    getLatLong().first,
                    getLatLong().second
                )



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

                    Toast.makeText(this,results.message,Toast.LENGTH_LONG).show()
                }
            }

        })
        val linearLayoutManagerProduct = GridLayoutManager(this, 2)

        productAdapter= ProductAdapter()
        binding.rec.adapter =    productAdapter
            binding.rec.layoutManager = linearLayoutManagerProduct

    }

    private fun renderData(data: Data?) {

        binding.textView60.setOnClickListener {
            val intent = Intent(this, MoreTagerActivity::class.java)
            intent.putExtra("tagerId", data?.merchant?.userId.toString())
            startActivity(intent)

        }
        binding.imageView38.setOnClickListener {
            data?.merchant?.snapchatLink?.let { it1 -> startLink(it1, this) }
        }

        binding.imageView40.setOnClickListener {
            data?.merchant?.facebookLink?.let { it1 -> startLink(it1, this) }
        }
        binding.imageView41.setOnClickListener {
            data?.merchant?.instagramLink?.let { it1 -> startLink(it1, this) }
        }
        binding.imageView38.setOnClickListener {
            data?.merchant?.whatsappLink?.let { it1 -> startLink(it1, this) }
        }

        binding.imageView44.setOnClickListener {
            SharePostLink(data?.merchant?.userId.toString())


        }



        binding.starButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {

                if (checkIsLogin()) {
                    likeButton?.isLiked?.let { viewModel.perFormLike(data?.merchant?.userId.toString()) }

                } else {
                    likeButton?.isLiked=false
                    val intent = Intent(this@TraderProfileActivity, LoginUser::class.java)
                    startActivity(intent)

                }
            }

            override fun unLiked(likeButton: LikeButton?) {

                likeButton?.isLiked?.let { viewModel.perFormUnLike(data?.merchant?.userId.toString()) }

            }

        })


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        val cat = data?.categories



        if (cat?.size ?: 0 > 0) {
            cat?.get(0)?.selected = true
        }
//        cat?.add(Category("","",10,"","","sa","",false))

        if(!flag){

        if(binding.RecCat.adapter?.itemCount?: 0<=0){


            binding.RecCat.adapter = CategoriesAdapter(cat) {
                callDependOnCat(it)

            }
            binding.RecCat.layoutManager = linearLayoutManager

        }

            if(!cat.isNullOrEmpty()){
                callDependOnCat(cat?.first())

            }


            flag=true
        }

        data?.products?.let { productAdapter?.setProduct(it) }






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
        binding.starButton.isLiked = data?.merchant?.favaurite == true
        binding.textView57.text = data?.merchant?.discountText

        Glide.with(this).load(data?.merchant?.imagePath.toString()).into(binding.banner)


        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.button.setOnClickListener {
            if (checkIsLogin()) {
                if (!edit) {


                    if (!binding.CommentText.text.toString().isNullOrEmpty()) {
                        pd.show()
                        viewModel.addComment(
                            getLoginData().data.token,
                            data?.merchant?.userId.toString(),
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
                            data?.merchant?.userId.toString(),
                            binding.ratingBar2.rating,
                            binding.CommentText.text.toString(),
                            CommentId,
                            getLatLong().first,
                            getLatLong().second
                        )
                        binding.CommentText.setText("")
                        binding.ratingBar2.rating = 0.0f

                    } else {
                        Toast.makeText(this, "اضف التعليق ", Toast.LENGTH_LONG).show()
                    }
                }
            }else
            {
                val intent = Intent(this@TraderProfileActivity, LoginUser::class.java)
                startActivity(intent)
            }

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

    private fun callDependOnCat(cat_id: Category?) {

        viewModel.getProductDetails(
            cat_id?.id.toString(),
            intent.getStringExtra("tagerId").toString(), getLatLong().first, getLatLong().second
        )


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
        fun startTagerProfile(productId: String, context: Context) {
            val intent = Intent(context, TraderProfileActivity::class.java)
            intent.putExtra("tagerId", productId)
            context.startActivity(intent)
        }
    }


}