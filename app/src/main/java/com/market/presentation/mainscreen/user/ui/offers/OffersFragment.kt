package com.market.presentation.mainscreen.user.ui.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.market.databinding.FragmentDashboardBinding
import com.market.presentation.mainscreen.user.ui.favorite.UserFavAdapter
import com.market.presentation.mainscreen.user.ui.home.sliderFragment.UserSliderFragment
import com.market.presentation.mainscreen.user.ui.offers.offeradapter.UserOfferAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OffersFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val adapter = UserOfferAdapter()
        binding.merchants.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.merchants.adapter = adapter

        activity?.let {
            val adapter = ScreenSlidePagerAdapter(it)
            binding.banner.adapter = adapter

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