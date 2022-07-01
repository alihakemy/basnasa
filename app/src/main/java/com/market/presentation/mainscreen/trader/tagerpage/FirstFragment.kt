package com.market.presentation.mainscreen.trader.tagerpage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.market.BuildConfig
import com.market.R
import com.market.data.models.get.tagerprofile.TagerProfile
import com.market.databinding.FragmentFirstBinding
import com.market.presentation.mainscreen.trader.addproduct.AddProduct
import com.market.presentation.mainscreen.trader.addproduct.AddProductViewModel
import com.market.presentation.mainscreen.trader.paymentpakages.PaymentPackagesActivity
import com.market.presentation.mainscreen.trader.showMyProfile.ShowMyTagerProfile
import com.market.presentation.mainscreen.trader.tagerpage.adapter.ProductAdapterTager
import com.market.presentation.mainscreen.user.displaytrader.tageradapter.ProductAdapter
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val viewModel: TagerPageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getTagerProfile()
                viewModel.results.observe(viewLifecycleOwner, Observer {
                    when (val results = it) {
                        is ResultState.Success<TagerProfile> -> {

                            Log.e("TagerProfile", results.data.toString())
                            renderTagerData(results.data)
                        }
                        else -> {
                            Toast.makeText(requireContext(), results.message.toString(), Toast.LENGTH_LONG)
                                .show()

                        }

                    }

                })

            }
        }


    }

    fun renderTagerData(data: TagerProfile?) {
        binding.inactive.setBackgroundResource(R.drawable.ic_path_47071)
        binding.pending.setBackgroundResource(R.drawable.ic_path_47071)
        binding.active.setBackgroundResource(R.drawable.ic_path_47071)

        Glide.with(requireContext()).load(data?.data?.merchant?.imagePath.toString())
            .into(binding.profileImage)

        binding.textView68.text = data?.data?.merchant?.name

        binding.count.text = data?.data?.merchant?.allProducts.toString()


        binding.profileImage.setOnClickListener {
            ShowMyTagerProfile.startTagerProfile(
                data?.data?.merchant?.userId.toString(),
                requireContext()
            )
        }

        binding.imageView56.setOnClickListener {
//            if (!BuildConfig.DEBUG) {
//                if (data?.data?.merchant?.packageCount!! > data?.data?.merchant?.allProducts!!) {
//                    val intent = Intent(requireContext(), AddProduct::class.java)
//                    requireContext().startActivity(intent)
//                } else {
//
//                    Toast.makeText(requireContext(), "شراء باقه اخرى ", Toast.LENGTH_LONG).show()
//                    val intent = Intent(requireContext(), PaymentPackagesActivity::class.java)
//                    context?.startActivity(intent)
//
//                }
//            } else {
//                val intent = Intent(requireContext(), AddProduct::class.java)
//                requireContext().startActivity(intent)
//            }

            if (data?.data?.merchant?.packageCount!! > data?.data?.merchant?.allProducts!!) {
                val intent = Intent(requireContext(), AddProduct::class.java)
                requireContext().startActivity(intent)
            } else {

                Toast.makeText(requireContext(), "شراء باقه اخرى ", Toast.LENGTH_LONG).show()
                val intent = Intent(requireContext(), PaymentPackagesActivity::class.java)
                context?.startActivity(intent)

            }

        }

        binding.textView73.text = data?.data?.merchant?.packageCount.toString()


        val linearLayoutManagerProduct = GridLayoutManager(requireContext(), 2)
        binding.rec.adapter = ProductAdapterTager(data?.data?.active)
        binding.rec.layoutManager = linearLayoutManagerProduct

        binding.active.setBackgroundResource(R.drawable.ic_rectangle_30094)

        binding.active.setOnClickListener {
            binding.active.setBackgroundResource(R.drawable.ic_rectangle_30094)
            binding.inactive.setBackgroundResource(R.drawable.ic_path_47071)
            binding.pending.setBackgroundResource(R.drawable.ic_path_47071)

            val linearLayoutManagerProduct = GridLayoutManager(requireContext(), 2)
            binding.rec.adapter = ProductAdapterTager(data?.data?.active)
            binding.rec.layoutManager = linearLayoutManagerProduct

        }
        binding.pending.setOnClickListener {
            binding.active.setBackgroundResource(R.drawable.ic_path_47071)
            binding.inactive.setBackgroundResource(R.drawable.ic_path_47071)
            binding.pending.setBackgroundResource(R.drawable.ic_rectangle_30094)
            val linearLayoutManagerProduct = GridLayoutManager(requireContext(), 2)
            binding.rec.adapter = ProductAdapterTager(data?.data?.pending)
            binding.rec.layoutManager = linearLayoutManagerProduct
        }
        binding.inactive.setOnClickListener {
            binding.active.setBackgroundResource(R.drawable.ic_path_47071)
            binding.inactive.setBackgroundResource(R.drawable.ic_rectangle_30094)
            binding.pending.setBackgroundResource(R.drawable.ic_path_47071)
            val linearLayoutManagerProduct = GridLayoutManager(requireContext(), 2)
            binding.rec.adapter = ProductAdapterTager(data?.data?.reject)
            binding.rec.layoutManager = linearLayoutManagerProduct

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}