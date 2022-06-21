package com.market.presentation.mainscreen.trader.paymentpakages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.market.BuildConfig
import com.market.data.models.get.paymentPackages.Package
import com.market.data.models.get.paymentPackages.PaymentPackages
import com.market.databinding.ActivityPaymentPackagesBinding
import com.market.presentation.bases.BaseActivity
import com.market.presentation.mainscreen.trader.paymentpakages.adapter.PackagesAdapter
import com.market.presentation.mainscreen.trader.paymentpakages.packagedetails.PackagePayment
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentPackagesActivity : BaseActivity() {
    lateinit var binding: ActivityPaymentPackagesBinding
    val viewModel: PaymentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentPackagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getPaymentPackages(getLatLong().first, getLatLong().second)

        viewModel.results.observe(this, Observer {
            when (val result = it) {

                is ResultState.Success<PaymentPackages> -> {

                    Log.e("PackagesALI", result.data.toString())

                    val list = result.data?.data?.packages
//
//                      list?.add(Package("2022-06-30",15,"sadad",
//                            20,100,"2022-06-30",
//                            "lkfld;skf","l;l;skgl;","l;kgl;s",false))
//


                    val linearLayoutManager = LinearLayoutManager(this)
                    linearLayoutManager.orientation = RecyclerView.VERTICAL

                    binding.recyclerView2.adapter = PackagesAdapter(list) { packages->

                        binding.textView80.setOnClickListener {

                            val intent =Intent(this, PackagePayment::class.java)
                            intent.putExtra("item",packages)
                            startActivity(intent)


                        }


                    }
                    binding.recyclerView2.layoutManager = linearLayoutManager
                }
                else -> {

                }

            }

        })


    }
}