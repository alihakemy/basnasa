package com.market.presentation.mainscreen.user.sections

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.market.BuildConfig
import com.market.data.models.get.setions.Merchant
import com.market.data.models.get.setions.Sections
import com.market.data.models.get.setions.SubCategory
import com.market.databinding.SectionsActivityBinding
import com.market.presentation.bases.BaseActivity
import com.market.presentation.location.MapsActivity
import com.market.presentation.mainscreen.notification.NotificationActivity
import com.market.presentation.mainscreen.user.search.SearchActivity
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SectionsActivity : BaseActivity() {
    lateinit var binding: SectionsActivityBinding
    val viewModels: SectionsViewModels by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SectionsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var coder = Geocoder(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                val addressList: List<Address> = coder.getFromLocation(
                    getLatLong().first.toDouble(),
                    getLatLong().second.toDouble(),
                    1
                )
                if (!addressList.isNullOrEmpty()) {
                    val location: Address = addressList[0]
                    binding.textView39.text = location.countryName.toString()

                }
            }

        }
        binding.imageView22.setOnClickListener {
            onBackPressed()
        }
        binding.imageView26.setOnClickListener {
            NotificationActivity.startNotification(this)
        }

        binding.textView39.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("role", "location")

            startActivity(intent)
        }

        binding.imageView31.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)

            startActivity(intent)
        }



            intent.getStringExtra("categoryId")?.let {
                viewModels.getSectionCategories(
                    it, getLoginData().data.token,
                    latitude = getLatLong().first, longitude = getLatLong().second
                )
            }


        viewModels.results.observe(this, Observer {

            when (val result = it) {
                is ResultState.Success<Sections> -> {
                    initCatAdapter(result?.data?.data?.subCategories)

                    initAdapter(result.data?.data?.merchants)


                }
                else -> {

                }
            }

        })

    }


    private fun initCatAdapter(merchants: List<SubCategory>?) {

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL

        binding.RecCat.layoutManager = linearLayoutManager

        binding.RecCat.adapter = CatAdapter(merchants) {
            getDependOnSubCat(it)
        }
    }

    private fun initAdapter(merchants: List<Merchant>?) {

        binding.rec.layoutManager = GridLayoutManager(this, 2)
        binding.rec.adapter = SectionsAdapter(merchants){boolean, id ->



            if (boolean) {
                viewModels.perFormLike(id)
            } else {
                viewModels.perFormUnLike(id)

            }
        }
    }

    private fun getDependOnSubCat(subCategory: SubCategory) {
        intent.getStringExtra("categoryId")?.let {
            viewModels.getSubSectionCategories(
                it, subcategoriesId = subCategory.id.toString(), getLoginData().data.token,
                latitude = getLatLong().first, longitude = getLatLong().second
            )
        }
    }

    companion object {
        fun startSections(categoryId: String, context: Context) {
            val intent = Intent(context, SectionsActivity::class.java)
            intent.putExtra("categoryId", categoryId)
            context.startActivity(intent)
        }
    }

}