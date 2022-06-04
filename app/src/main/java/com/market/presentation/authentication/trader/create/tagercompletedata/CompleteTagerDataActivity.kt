package com.market.presentation.authentication.trader.create.tagercompletedata

import android.R
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide.with
import com.github.dhaval2404.imagepicker.ImagePicker
import com.market.data.models.SendCompleteJoin
import com.market.data.models.get.categories.Categories
import com.market.data.models.get.categories.Category
import com.market.databinding.ActivityCompleteTagerDataBinding
import com.market.presentation.location.MapsActivity
import com.market.utils.ResultState
import com.market.utils.mFileUtils
import com.market.utils.prepareFilePart
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EarlyEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class CompleteTagerDataActivity : AppCompatActivity() {

    val viewModel: TagerCompleteViewModel by viewModels()
    private lateinit var binding: ActivityCompleteTagerDataBinding

    var imageUrl: String? = null
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data

                    imageUrl = fileUri.toString()
                    binding.imageView7.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                }
                else -> {
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteTagerDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView3.setOnClickListener {
            onBackPressed()
        }

        binding.imageView7.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        viewModel.categories.observe(this, Observer {

            when (val result = it) {

                is ResultState.Success<Categories> -> {
                    val list: ArrayList<String> = ArrayList()
                    result.data.data.categories.forEach {
                        list.add(it.name)
                    }

                    val adapter = ArrayAdapter(
                        this,
                        R.layout.simple_list_item_1,
                        list
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                    binding.cat.setAdapter(adapter)
                }
                else -> {

                }


            }

        })

        binding.location.setOnClickListener {

            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("role", "location")

            startActivity(intent)


        }

        binding.button.setOnClickListener {

            Log.e("PathAAPATH",imageUrl.toString())

                viewModel.uploadStore(SendCompleteJoin("17","sdf","dsf","sdf",
                "sdfd","dsf","sdf",
                "sf","sf","sdfsf",
                "55778811"),"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiODY4MzhmMzE4NmEyN2ZiMmE5ZDc1MzQ5MDk1ZGNhMWNhNTYxNzJiNTE0YTFiZGViMjkyMDIyNDliNDI1YzBmZTZiMDNkMzVhYWJkNGViOWYiLCJpYXQiOjE2NTQyODkyOTMsIm5iZiI6MTY1NDI4OTI5MywiZXhwIjoxNjg1ODI1MjkzLCJzdWIiOiI2MTA4Iiwic2NvcGVzIjpbXX0.cji6J0DXhvn-tDu5kgL_5jn5BzbdLZRG_4Pn4dHD-A4AqRnRtA7H9NUuZKfDi5xK49OY5r1Fw7u4G05jVFAu_6Wm_muyo8C7ruSS9fJA4jzO0X6z9jnltjP2z8nocyS_aa5OJU-wKtqmrqMD62U-0C7lz0lLhzKNcKygFY3YErOEmA8EnwNCqp50hYQ-kL37JNXuXNV-9KkqX9oZ92V5uWStzGIvwgZhCau9UFU2JJ1lgOczS2tvdSPENAey8ZBmQ_p8m1daEWH448BlXOKMBrixsZaf_a8QTIXVuE_r94u1yHOxQDPS2xngL8GMZNMuUS_HyLEXsHJ_7CjQ1UeeRvz3naPEwSpuuCPgLODJxep8ubzKK1C6cfwTQWXAnnFLNRrV3agrvg6gKPHMEBrGGr-7gqKh5rQhuCkQNpMi6UWIykvarenw8ZZwp6eJXR890AyAH09lKQhbb2DX2cHj-l3HMQyGuWyRXi0wwESs2jIK4ngDsFm-Is2JGJwZZOWrSHIfWwWIt6o8yBmXU9VD9cVMj5z4pKYkrVzC8vn-JAh2m_N8JPQvRJ7jgSFMoOWISe2WhBYcc-CeysSobbXUVsn5tznWihPnfqtjLueBzWhFjWsjxeeH-r96B8Um2s3hc1Onzlret58F5cxjZaEoFgR7BJ5IC3hGO7hqUvjAj1s"
                ,
                    prepareFilePart("image", mFileUtils(this).getPath(imageUrl.toString().toUri(),this),
                    ))



        }


    }
}