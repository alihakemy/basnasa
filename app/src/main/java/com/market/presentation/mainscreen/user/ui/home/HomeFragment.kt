package com.market.presentation.mainscreen.user.ui.home

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
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
import com.market.R
import com.market.data.models.get.homeusers.*
import com.market.data.models.get.links.SocialLinks
import com.market.data.models.get.popups.popups
import com.market.databinding.FragmentHomeBinding
import com.market.popups.DialogPopUp
import com.market.presentation.location.MapsActivity
import com.market.presentation.mainscreen.notification.NotificationActivity
import com.market.presentation.mainscreen.user.MainActivityUser
import com.market.presentation.mainscreen.user.search.SearchActivity
import com.market.presentation.mainscreen.user.ui.home.categories.UserCategoriesAdapter
import com.market.presentation.mainscreen.user.ui.home.merchants.UserMerchantsAdapter
import com.market.presentation.mainscreen.user.ui.home.product.UserProductsAdapter
import com.market.presentation.mainscreen.user.ui.home.sliderFragment.UserSliderFragment
import com.market.presentation.mainscreen.user.ui.offers.SliderFragment
import com.market.utils.ResultState
import com.market.utils.enableAutoScroll
import com.market.utils.startLink
import com.romainpiel.shimmer.Shimmer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    var mActivity: MainActivityUser? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var coder = Geocoder(requireContext())
        activity?.let {
            mActivity = (it as MainActivityUser)
        }

        val shimmer = Shimmer()
        shimmer.start(binding.shimmerTv)
        val name = mActivity?.getLoginData()?.data?.user?.name.toString()
        binding.shimmerTv.text = getString(R.string.welcome) + " " + name

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                mActivity?.getLatLong()?.first?.let {
                    mActivity?.getLatLong()?.second?.let { it1 ->
                        homeViewModel.getHomeScreen(
                            latitude = it,
                            longitude = it1
                        )
                    }
                }
                try {

                    val addressList: List<Address> =
                        mActivity?.getLatLong()?.first?.toDouble()?.let {
                            mActivity?.getLatLong()?.second?.toDouble()?.let { it1 ->
                                coder.getFromLocation(
                                    it,
                                    it1,
                                    1
                                )
                            }
                        } ?: emptyList()
                    if (!addressList.isNullOrEmpty()) {
                        val location: Address = addressList[0]
                        binding.textView39.text = location.countryName.toString()

                    }
                } catch (e: Exception) {

                }

            }
        }


        binding.refresh.setOnRefreshListener {

            mActivity?.getLatLong()?.first?.let {
                mActivity?.getLatLong()?.second?.let { it1 ->
                    homeViewModel.getHomeScreen(
                        latitude = it,
                        longitude = it1
                    )
                }
            }
            try {

                val addressList: List<Address> = mActivity?.getLatLong()?.first?.toDouble()?.let {
                    mActivity?.getLatLong()?.second?.toDouble()?.let { it1 ->
                        coder.getFromLocation(
                            it,
                            it1,
                            1
                        )
                    }
                } ?: emptyList()
                if (!addressList.isNullOrEmpty()) {
                    val location: Address = addressList[0]
                    binding.textView39.text = location.countryName.toString()

                }
            } catch (e: Exception) {

            }
        }

        binding.searchText.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)

            startActivity(intent)
        }

        binding.textView39.setOnClickListener {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            intent.putExtra("role", "location")

            startActivity(intent)
        }
        binding.imageView20.setOnClickListener {
            NotificationActivity.startNotification(requireContext())
        }


        homeViewModel.results.observe(viewLifecycleOwner) {

            when (val results = it) {
                is ResultState.Success<HomeUser> -> {
                    binding.refresh.isRefreshing = false


                    results.data?.data?.categories?.let { it1 -> initCategories(it1) }

                    results?.data?.data?.products?.let { it1 -> initProducts(it1) }

                    initMerchants(results.data?.data?.merchants)



                    results.data?.data?.slider?.let { it1 -> initSlider(it1) }
                    results.data?.data?.banner?.let { it1 -> initBanner(it1) }


                }
                else -> {

                }
            }
        }




        homeViewModel?.getLinks()?.observe(viewLifecycleOwner, Observer {

            when (val result = it) {

                is ResultState.Success<SocialLinks> -> {

                    binding.imageView34.setOnClickListener {
                        result.data?.data?.facebook?.let { it1 -> startLink(it1, requireContext()) }
                    }

                    binding.imageView35.setOnClickListener {
                        result.data?.data?.twitter?.let { it1 -> startLink(it1, requireContext()) }
                    }

                    binding.imageView36.setOnClickListener {
                        val url = "https://api.whatsapp.com/send?phone=" + result.data?.data?.phone
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(i)
                    }


                    binding.imageView37.setOnClickListener {
                        result.data?.data?.instgrame?.let { it1 ->
                            startLink(
                                it1,
                                requireContext()
                            )
                        }
                    }


                }
                else -> {

                }

            }


        })




        return root
    }

    private fun initBanner(banner: List<Banner>) {
        activity?.let { activity ->
            kotlin.runCatching {
                binding.banner.adapter = ScreenSlidePagerAdapters(activity, banner)

                TabLayoutMediator(binding.tabLayout, binding.banner) { tab, position ->
                }.attach()
            }.onFailure {
                kotlin.runCatching {
                    binding.banner.adapter = ScreenSlidePagerAdapters(activity, banner)

                    TabLayoutMediator(binding.tabLayout, binding.banner) { tab, position ->
                    }.attach()
                }
            }

        }
    }

    private inner class ScreenSlidePagerAdapters(fa: FragmentActivity, val banner: List<Banner>) :
        FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = banner?.size ?: 0
        override fun createFragment(position: Int): Fragment =
            SliderFragment.newInstance(
                banner[position].imagePath.toString(),
                banner[position].id.toString(),
                banner[position].typeDirection.toString(),
                banner[position].showNumber.toString()
            )
    }

    private fun initMerchants(merchants: List<Merchant>?) {
        binding.merchants.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.merchants.adapter = UserMerchantsAdapter(merchants)

    }

    private fun initProducts(products: List<Product>) {
        binding.products.layoutManager = LinearLayoutManager(requireContext())
        binding.products.adapter = UserProductsAdapter(products)
    }

    private fun initCategories(categories: List<Category>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())

        binding.categories.layoutManager = layoutManager
        binding.categories.adapter = UserCategoriesAdapter(categories)

    }

    private fun initSlider(slider: List<Slider>) {
        activity?.let { activity ->
            kotlin.runCatching {
                val adapter = ScreenSlidePagerAdapter(activity, slider)
                binding.sliderViewPager.adapter = adapter
                binding.sliderViewPager.enableAutoScroll(slider.size)
            }.onFailure {
                kotlin.runCatching {
                    val adapter = ScreenSlidePagerAdapter(activity, slider)
                    binding.sliderViewPager.adapter = adapter
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity, val slider: List<Slider>) :
        FragmentStateAdapter(fa) {


        override fun getItemCount(): Int = slider.size

        override fun createFragment(position: Int): Fragment =
            UserSliderFragment.newInstance(
                slider.get(position).imagePath.toString(),
                slider.get(position).id.toString(),
                slider[position].typeDirection.toString(),
                slider[position].showNumber.toString()
            )
    }
}