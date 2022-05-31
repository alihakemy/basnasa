package com.market.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.market.data.models.get.OnBoardingGet
import com.market.databinding.OnBoardingActivityBinding
import com.market.presentation.authentication.user.login.LoginUser
import com.market.presentation.bases.BaseActivity
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoarding : BaseActivity() {

    private lateinit var binding: OnBoardingActivityBinding

    private val viewModel: OnBoardingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = OnBoardingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.parent.visibility=View.GONE




        viewModel.onBoardingResult.observe(this, Observer {

            when (val result = it) {
                is ResultState.Success<OnBoardingGet> -> {
                    val adapter = ViewPagerAdapter(this, result.data)

                    init(adapter)
                    binding.parent.visibility=View.VISIBLE

                }
                else -> {

                }

            }


        })


    }

    private fun init(adapter: ViewPagerAdapter) {
        binding.viewPager2.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager2)
        binding.imageView.setOnClickListener {
            binding.viewPager2.currentItem = binding.viewPager2.currentItem.plus(1)
        }


        binding.imageView2.setOnClickListener {
            val next = binding.viewPager2.currentItem.minus(1)
            binding.viewPager2.currentItem = next
            if (next < 0) {
                startLogin()
            }
        }

        binding.textView.setOnClickListener {

            startLogin()

        }
        binding.viewPager2.currentItem = adapter.count
        if (binding.viewPager2.currentItem == adapter.count - 1) {
            binding.imageView.visibility = View.GONE
        }

        binding.viewPager2.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position < adapter.count - 1) {
                    binding.imageView.visibility = View.VISIBLE
                    binding.textView.visibility = View.GONE
                } else if (position == adapter.count - 1) {

                    binding.imageView.visibility = View.GONE
                    binding.textView.visibility = View.VISIBLE
                }

            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    private fun startLogin() {
        val intent = Intent(this, LoginUser::class.java)
        startActivity(intent)
        finish()
    }
}