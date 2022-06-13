package com.market.presentation.mainscreen.user.ui.home

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.market.data.models.get.homeusers.*
import com.market.databinding.FragmentHomeBinding
import com.market.presentation.location.MapsActivity
import com.market.presentation.mainscreen.user.MainActivityUser
import com.market.presentation.mainscreen.user.search.SearchActivity
import com.market.presentation.mainscreen.user.search.adapter.SearchAdapter
import com.market.presentation.mainscreen.user.ui.home.categories.UserCategoriesAdapter
import com.market.presentation.mainscreen.user.ui.home.merchants.UserMerchantsAdapter
import com.market.presentation.mainscreen.user.ui.home.product.UserProductsAdapter
import com.market.presentation.mainscreen.user.ui.home.sliderFragment.UserSliderFragment
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        val activity = (activity as MainActivityUser)


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                val addressList: List<Address> = coder.getFromLocation(
                    activity.getLatLong().first.toDouble(),
                    activity.getLatLong().second.toDouble(),
                    1
                )
                if (!addressList.isNullOrEmpty()) {
                    val location: Address = addressList[0]
                    binding.textView39.text = location.countryName.toString()

                }
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

        homeViewModel.getHomeScreen(
            latitude = activity.getLatLong().first,
            longitude = activity.getLatLong().second
        )

        homeViewModel.results.observe(viewLifecycleOwner) {

            when (val results = it) {
                is ResultState.Success<HomeUser> -> {


                    results.data?.data?.slider?.let { it1 -> initSlider(it1) }

                    results.data?.data?.categories?.let { it1 -> initCategories(it1) }

                    results?.data?.data?.products?.let { it1 -> initProducts(it1) }

                    initMerchants(results.data?.data?.merchants)
                    results.data?.data?.banner?.let { it1 -> initBanner(it1) }


                }
                else -> {

                }
            }
        }







        return root
    }

    private fun initBanner(banner: List<Banner>) {
        activity?.let {
            binding.banner.adapter = ScreenSliderPagerAdapter(it, banner)

            TabLayoutMediator(binding.tabLayout, binding.banner) { tab, position ->
            }.attach()
        }
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
        activity?.let {
            val adapter = ScreenSlidePagerAdapter(it, slider)
            binding.sliderViewPager.adapter = adapter


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class ScreenSliderPagerAdapter(fa: FragmentActivity, val slider: List<Banner>) :
        FragmentStateAdapter(fa) {


        override fun getItemCount(): Int = slider.size

        override fun createFragment(position: Int): Fragment =
            UserSliderFragment.newInstance(
                slider.get(position).imagePath,
                slider.get(position).id.toString()
            )
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity, val slider: List<Slider>) :
        FragmentStateAdapter(fa) {


        override fun getItemCount(): Int = slider.size

        override fun createFragment(position: Int): Fragment =
            UserSliderFragment.newInstance(
                slider.get(position).imagePath,
                slider.get(position).id.toString()
            )
    }
}