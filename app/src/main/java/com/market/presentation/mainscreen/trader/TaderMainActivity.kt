package com.market.presentation.mainscreen.trader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.market.databinding.ActivityTaderMainBinding
import com.market.presentation.bases.BaseActivity
import com.market.presentation.mainscreen.trader.tagerdata.SecondFragment
import com.market.presentation.mainscreen.trader.tagerpage.FirstFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaderMainActivity : BaseActivity() {

    private lateinit var binding: ActivityTaderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityTaderMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ScreenSlidePagerAdapter(this)
        binding.viewPager22.adapter = adapter
        binding.store.setCardBackgroundColor(android.graphics.Color.parseColor("#ebd7dd"))
        binding.setting.setCardBackgroundColor(android.graphics.Color.parseColor("#00000000"))
        binding.viewPager22.isUserInputEnabled = false

        binding.setting.setOnClickListener {
            binding.setting.setCardBackgroundColor(android.graphics.Color.parseColor("#ebd7dd"))
            binding.store.setCardBackgroundColor(android.graphics.Color.parseColor("#00000000"))
            binding.viewPager22.currentItem = 1

        }
        binding.store.setOnClickListener {
            binding.store.setCardBackgroundColor(android.graphics.Color.parseColor("#ebd7dd"))
            binding.setting.setCardBackgroundColor(android.graphics.Color.parseColor("#00000000"))

            binding.viewPager22.currentItem = 0
        }


    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) :
        FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment {

            return if (position == 0) {
                FirstFragment()

            } else {

                SecondFragment()
            }

        }


    }

}