package com.market.presentation.mainscreen.trader.tagerdata

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.market.BuildConfig
import com.market.R
import com.market.data.models.get.tagerprofile.TagerProfile
import com.market.databinding.FragmentSecondBinding
import com.market.presentation.mainscreen.trader.addproduct.AddProduct
import com.market.presentation.mainscreen.trader.paymentpakages.PaymentPackagesActivity
import com.market.presentation.mainscreen.trader.tagerpage.TagerPageViewModel
import com.market.presentation.mainscreen.trader.tagerpage.adapter.ProductAdapterTager
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val viewModel: TagerPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView77.setOnClickListener {
            val intent = Intent(requireContext(), PaymentPackagesActivity::class.java)
            context?.startActivity(intent)

        }
        viewModel.getTagerProfile()

        viewModel.results.observe(viewLifecycleOwner, Observer {
            when (val results = it) {
                is ResultState.Success<TagerProfile> -> {

                    renderTagerData(results.data)
                }
                else -> {
                    Toast.makeText(requireContext(), results.message.toString() , Toast.LENGTH_LONG).show()

                }

            }

        })

    }

    private fun renderTagerData(data: TagerProfile?) {
        Log.e("TagerProfileREnder", data.toString())

        Glide.with(requireContext()).load(data?.data?.merchant?.imagePath.toString())
            .into(binding.profileImage)

        binding.textView68.text = data?.data?.merchant?.name


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}