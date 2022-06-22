package com.market.presentation.mainscreen.trader.addproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.market.R
import com.market.data.models.get.categories.Categories
import com.market.databinding.ActivityAddProductBinding
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class AddProduct : AppCompatActivity() {
    lateinit var binding: ActivityAddProductBinding
    val viewModel: AddProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.categories.observe(this, Observer {

            when (val result = it) {

                is ResultState.Success<Categories> -> {
                    val list: ArrayList<String> = ArrayList()
                    result.data?.data?.categories?.forEach {
                        list.add(it.name)
                    }

                    val adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        list
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.cat.adapter = adapter


                }
                else -> {

                }


            }

        })

    }
}