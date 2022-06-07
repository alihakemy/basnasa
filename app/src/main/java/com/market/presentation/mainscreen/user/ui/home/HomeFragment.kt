package com.market.presentation.mainscreen.user.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.market.databinding.FragmentHomeBinding
import com.market.presentation.mainscreen.user.ui.home.categories.UserCategoriesAdapter
import com.market.presentation.mainscreen.user.ui.home.merchants.UserMerchantsAdapter
import com.market.presentation.mainscreen.user.ui.home.product.UserProductsAdapter
import com.market.presentation.mainscreen.user.ui.home.sliderFragment.UserSliderFragment


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
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.text.observe(viewLifecycleOwner) {

        }
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())

        binding.categories.layoutManager = layoutManager
        binding.categories.adapter = UserCategoriesAdapter()


        binding.products.layoutManager = LinearLayoutManager(requireContext())
        binding.products.adapter = UserProductsAdapter()

        binding.merchants.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.merchants.adapter = UserMerchantsAdapter()

        activity?.let {
            val adapter = ScreenSlidePagerAdapter(it)
            binding.sliderViewPager.adapter = adapter

            binding.banner.adapter = ScreenSlidePagerAdapter(it)

            TabLayoutMediator(binding.tabLayout, binding.banner) { tab, position ->
            }.attach()

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 10

        override fun createFragment(position: Int): Fragment = UserSliderFragment()
    }
}