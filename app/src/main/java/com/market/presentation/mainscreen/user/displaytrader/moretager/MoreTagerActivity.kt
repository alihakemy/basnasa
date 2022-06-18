package com.market.presentation.mainscreen.user.displaytrader.moretager

import android.app.ProgressDialog
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.like.LikeButton
import com.like.OnLikeListener
import com.market.R
import com.market.data.models.get.tagerdetails.Data
import com.market.data.models.get.tagerdetails.TagerDetails
import com.market.databinding.ActivityMoreTagerBinding
import com.market.presentation.bases.BaseActivity
import com.market.presentation.mainscreen.user.displaytrader.TagerDetailsViewModel
import com.market.utils.ResultState
import com.market.utils.startLink
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoreTagerActivity : BaseActivity() {
    lateinit var binding: ActivityMoreTagerBinding
    lateinit var pd: ProgressDialog
    val viewModel: TagerDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreTagerBinding.inflate(layoutInflater)
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
                    results.data?.data?.categories?.forEach {
                        it.name?.let { it1 -> addChip(it1.toString()) }

                    }

                }
                else -> {

                }
            }

        })

    }

    private fun renderData(data: Data?) {
        var coder = Geocoder(this)

        binding.imageView38.setOnClickListener {
            data?.merchant?.snapchatLink?.let { it1 -> startLink(it1,this) }
        }

        binding.imageView40.setOnClickListener {
            data?.merchant?.facebookLink?.let { it1 -> startLink(it1,this) }
        }
        binding.imageView41.setOnClickListener {
            data?.merchant?.instagramLink?.let { it1 -> startLink(it1,this) }
        }
        binding.imageView42.setOnClickListener {
            data?.merchant?.whatsappLink?.let { it1 -> startLink(it1,this) }
        }
        binding.starButton.isLiked = data?.merchant?.favaurite == true
        Glide.with(this).load(data?.merchant?.bannerPath.toString()).into(binding.banner)
        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.textView56.text=data?.merchant?.name.toString()

        binding.textView66.text = "(" + data?.merchant?.rateCount + ")"

        val addressList: List<Address> = coder.getFromLocation(
            data?.merchant?.lat?.toDouble()?:0.0,
            data?.merchant?.long?.toDouble()?:0.0,
            1
        )
        if (!addressList.isNullOrEmpty()) {
            val location: Address = addressList[0]
            binding.textView.text = location.countryName.toString()
        }

        binding.info.text= data?.merchant?.content.toString()
        kotlin.runCatching {
            binding.rate.rating = (data?.merchant?.rate ?: 0f) as Float
        }

        binding.starButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {

                likeButton?.isLiked?.let { viewModel.perFormLike(data?.merchant?.userId.toString()) }
            }

            override fun unLiked(likeButton: LikeButton?) {

                likeButton?.isLiked?.let { viewModel.perFormUnLike(data?.merchant?.userId.toString()) }

            }

        })
        binding.imageView39.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=" + data?.merchant?.phone
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }


    }
    private fun addChip(pItem: String) {

        val chip = layoutInflater.inflate(R.layout.chipss, binding.cat, false) as TextView

        chip.text=pItem
        binding.cat.addView(chip)
    }

}