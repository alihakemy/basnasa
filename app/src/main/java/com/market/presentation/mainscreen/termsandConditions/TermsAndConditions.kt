package com.market.presentation.mainscreen.termsandConditions

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.market.R
import com.market.data.models.get.terms.TermsCondtionModel
import com.market.databinding.ActivityTermsAndConditionsBinding
import com.market.presentation.bases.BaseActivity
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsAndConditions : BaseActivity() {

    val viewModels: TermConditionViewModels by viewModels()
    lateinit var binding: ActivityTermsAndConditionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsAndConditionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.extras?.getString("type").toString()

        viewModels.getPages(type)

        if (type.equals("1")) {

        } else {
            binding.textView51.text = "About Us"
            binding.textView96.text = "About"

        }

        viewModels.results.observe(this, Observer {


            when (val result = it) {
                is ResultState.Success<TermsCondtionModel> -> {
                    binding.textView96.text=result.data?.data?.name.toString()
                    binding.textView98.text=result.data?.data?.descrption.toString()

                }
                else -> {
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_LONG).show()

                }
            }

        })


    }

    companion object {
        fun startTerms(type: String, context: Context) {
            val intent = Intent(context, TermsAndConditions::class.java)
            intent.putExtra("type", type)
            context.startActivity(intent)
        }
    }
}