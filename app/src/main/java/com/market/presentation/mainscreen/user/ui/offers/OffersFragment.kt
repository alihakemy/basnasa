package com.market.presentation.mainscreen.user.ui.offers

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.market.data.models.get.links.SocialLinks
import com.market.data.models.get.offers.Banner
import com.market.data.models.get.offers.Merchant
import com.market.data.models.get.offers.Offers
import com.market.data.models.get.offers.SubCategory
import com.market.databinding.FragmentDashboardBinding
import com.market.presentation.location.MapsActivity
import com.market.presentation.mainscreen.notification.NotificationActivity
import com.market.presentation.mainscreen.user.MainActivityUser
import com.market.presentation.mainscreen.user.search.SearchActivity
import com.market.presentation.mainscreen.user.ui.offers.offeradapter.UserHorizontalAdapter
import com.market.presentation.mainscreen.user.ui.offers.offeradapter.UserOfferAdapter
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.annotation.meta.When

@AndroidEntryPoint
class OffersFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var merchant: ArrayList<Merchant>? = null
    var flag = true
    var viewModel: OfferViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[OfferViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        var coder = Geocoder(requireContext())

        val activitys = (activity as MainActivityUser)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                val addressList: List<Address> = coder.getFromLocation(
                    activitys.getLatLong().first.toDouble(),
                    activitys.getLatLong().second.toDouble(),
                    1
                )
                if (!addressList.isNullOrEmpty()) {
                    val location: Address = addressList[0]
                    binding.textView39.text = location.countryName.toString()
                }
            }

        }
        binding.linearLayout12.setOnClickListener {

            val intent = Intent(requireContext(), MapsActivity::class.java)
            intent.putExtra("role", "location")

            startActivity(intent)


        }
        binding.imageView31.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }

        binding.imageView26.setOnClickListener {
            NotificationActivity.startNotification(requireContext())
        }

        viewModel?.getOffers(activitys.getLatLong().first, activitys.getLatLong().second)


        viewModel?.results?.observe(viewLifecycleOwner, Observer {

            when (val result = it) {
                is ResultState.Success<Offers> -> {

                    initCatAdapter(result.data?.data?.subCategories)
                    activity?.let {


                        binding.banner.adapter = ScreenSlidePagerAdapter(
                            it,
                            result?.data?.data?.banner
                        )

                        TabLayoutMediator(binding.tabLayout, binding.banner) { tab, position ->
                        }.attach()

                    }
                    Log.e("OFFERSALISAMI", result.data?.data?.merchants.toString())

                    initMercant(result.data?.data?.merchants)

                    merchant = result.data?.data?.merchants as ArrayList<Merchant>?
                    initClickChange()
                }
                else -> {

                }
            }

        })





        return root
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
        val adapter = UserOfferAdapter(merchants) { boolean, id ->

            if (boolean) {
                viewModel?.perFormLike(id)
            } else {
                viewModel?.perFormUnLike(id)

            }
        }
        binding.merchants.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.merchants.adapter = adapter
    }

    private fun initMerchantsAdapterHorizontalAdapter(merchants: List<Merchant>?) {

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = RecyclerView.VERTICAL

        binding.merchants.layoutManager = linearLayoutManager

        binding.merchants.adapter = UserHorizontalAdapter(merchants)
    }

    private fun initCatAdapter(subCategory: List<com.market.data.models.get.offers.SubCategory>?) {

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL

        binding.subcat.layoutManager = linearLayoutManager

        binding.subcat.adapter = SubCatAdapter(subCategory) {

            getDependsOnSubCat(it)
        }
    }

    private fun getDependsOnSubCat(subCategory: SubCategory?) {
        val activitys = (activity as MainActivityUser)

        viewModel?.getOffers(
            activitys.getLatLong().first, activitys.getLatLong().second,
            subCategory?.id.toString()
        )

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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