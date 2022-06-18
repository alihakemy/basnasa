package com.market.presentation.mainscreen.user.ui.offers

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.market.BuildConfig
import com.market.R
import com.market.data.models.get.offers.Banner
import com.market.data.models.get.offers.Merchant
import com.market.data.models.get.offers.Offers
import com.market.data.models.get.offers.SubCategory
import com.market.databinding.ActivityOffersBinding
import com.market.databinding.FragmentDashboardBinding
import com.market.presentation.bases.BaseActivity
import com.market.presentation.location.MapsActivity
import com.market.presentation.mainscreen.user.MainActivityUser
import com.market.presentation.mainscreen.user.search.SearchActivity
import com.market.presentation.mainscreen.user.ui.offers.offeradapter.UserHorizontalAdapter
import com.market.presentation.mainscreen.user.ui.offers.offeradapter.UserOfferAdapter
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OffersActivity :BaseActivity() {
    lateinit var binding:ActivityOffersBinding
    // This property is only valid between onCreateView and
    // onDestroyView.

    var merchant: ArrayList<Merchant>? = null
    var flag = true
    var viewModel: OfferViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOffersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this)[OfferViewModel::class.java]


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
        binding.linearLayout12.setOnClickListener {

            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("role", "location")

            startActivity(intent)


        }
        binding.imageView31.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        if (BuildConfig.DEBUG) {
            viewModel?.getOffers("29", "48")

        } else {
            viewModel?.getOffers(getLatLong().first, getLatLong().second)

        }
        viewModel?.results?.observe(this, Observer {

            when (val result = it) {
                is ResultState.Success<Offers> -> {

                    initCatAdapter(result.data?.data?.subCategories)



                        binding.banner.adapter = ScreenSlidePagerAdapter(
                            this,
                            result?.data?.data?.banner
                        )

                        TabLayoutMediator(binding.tabLayout, binding.banner) { tab, position ->
                        }.attach()


                    Log.e("OFFERSALISAMI", result.data?.data?.merchants.toString())

                    initMercant(result.data?.data?.merchants)

                    merchant = result.data?.data?.merchants as ArrayList<Merchant>?
                    initClickChange()
                }
                else -> {

                }
            }

        })

    }
    private fun initClickChange() {
        merchant?.let {
            binding.imageView32.setOnClickListener {
                flag = if (flag) {
                    binding.imageView32.setImageResource(R.drawable.ic_group_96504)

                    initMerchantsAdapterHorizontalAdapter(merchant)
                    false

                } else {
                    binding.imageView32.setImageResource(R.drawable.ic_group_898)

                    initMercant(merchant)
                    true


                }


            }
        }
    }

    private fun initMercant(merchants: List<Merchant>?) {
        val adapter = UserOfferAdapter(merchants){boolean, id ->

            if (boolean) {
                viewModel?.perFormLike(id)
            } else {
                viewModel?.perFormUnLike(id)

            }
        }
        binding.merchants.layoutManager = GridLayoutManager(this, 2)
        binding.merchants.adapter = adapter
    }

    private fun initMerchantsAdapterHorizontalAdapter(merchants: List<Merchant>?) {

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL

        binding.merchants.layoutManager = linearLayoutManager

        binding.merchants.adapter = UserHorizontalAdapter(merchants)
    }

    private fun initCatAdapter(subCategory: List<com.market.data.models.get.offers.SubCategory>?) {

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL

        binding.subcat.layoutManager = linearLayoutManager

        binding.subcat.adapter = SubCatAdapter(subCategory) {

            getDependsOnSubCat(it)
        }
    }

    private fun getDependsOnSubCat(subCategory: SubCategory?) {

        viewModel?.getOffers(getLatLong().first, getLatLong().second,
            subCategory?.id.toString()
        )

    }






    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity, val banner: List<Banner>?) :
        FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = banner?.size ?: 0
        override fun createFragment(position: Int): Fragment =
            SliderFragment.newInstance(
                banner?.get(position)?.imagePath.toString(),
                banner?.get(position)?.id.toString()
            )
    }
}