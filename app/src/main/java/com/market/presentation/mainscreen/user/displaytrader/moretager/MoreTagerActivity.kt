package com.market.presentation.mainscreen.user.displaytrader.moretager

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.market.R
import com.market.data.models.get.tagerdetails.TagerDetails
import com.market.databinding.ActivityMoreTagerBinding
import com.market.presentation.bases.BaseActivity
import com.market.presentation.mainscreen.user.displaytrader.TagerDetailsViewModel
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreTagerActivity : BaseActivity() {
    lateinit var binding: ActivityMoreTagerBinding
    lateinit var pd: ProgressDialog
    val viewModel: TagerDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreTagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pd = ProgressDialog(this)
        pd.setMessage("loading")
        pd.setCancelable(false)
        pd.show()

        viewModel.getProductDetails(
            intent.getStringExtra("tagerId").toString(),
            getLatLong().first,
            getLatLong().second
        )
        viewModel.results.observe(this, androidx.lifecycle.Observer {
            if (pd.isShowing) {
                pd.dismiss()
            }
            when (val results = it) {
                is ResultState.Success<TagerDetails> -> {


                }
                else -> {

                }
            }

        })

    }
}