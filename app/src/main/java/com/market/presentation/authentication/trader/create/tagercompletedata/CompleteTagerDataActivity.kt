package com.market.presentation.authentication.trader.create.tagercompletedata

import android.R
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide.with
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.navigation.NavigationBarView
import com.market.data.models.SendCompleteJoin
import com.market.data.models.get.categories.Categories
import com.market.data.models.get.categories.Category
import com.market.databinding.ActivityCompleteTagerDataBinding
import com.market.presentation.bases.BaseActivity
import com.market.presentation.location.MapsActivity
import com.market.utils.ResultState
import com.market.utils.mFileUtils
import com.market.utils.prepareFilePart
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EarlyEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class CompleteTagerDataActivity : BaseActivity() {

    val viewModel: TagerCompleteViewModel by viewModels()
    private lateinit var binding: ActivityCompleteTagerDataBinding
    lateinit var listCat: ArrayList<Category>
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
        val pd = ProgressDialog(this)
        pd.setMessage("loading")
        pd.setCancelable(false)
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
                    listCat = result.data!!.data.categories
                    result.data.data.categories.forEach {
                        list.add(it.name)
                    }

                    val adapter = ArrayAdapter(
                        this,
                        R.layout.simple_list_item_1,
                        list
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.cat.adapter = adapter


                }
                else -> {

                }


            }

        })

        viewModel.status.observe(this, Observer {
            if (pd.isShowing) {
                pd.dismiss()
            }

            if (it.equals("Sucess")) {

                binding.include.parent.visibility = View.VISIBLE
                binding.include.done.setOnClickListener {
                    finish()
                }


            } else {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()

            }

        })
        binding.location.setOnClickListener {

            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("role", "location")

            startActivity(intent)


        }

        binding.button.setOnClickListener {


            var categoriesId = 0

            listCat.forEach {

                if (it.name.equals(binding.cat.selectedItem.toString().toString())) {
                    categoriesId = it.id
                }

            }
            imageUrl?.let {
                if (getLatLong().first.equals("0")) {
                    Toast.makeText(this, "اكمل بيانتاتك ", Toast.LENGTH_LONG).show()

                } else {
                    pd.show()
                    viewModel.uploadStore(
                        SendCompleteJoin(
                            categoriesId.toString(),
                            arrivaltime = binding.arrivalTime.text.toString(),
                            binding.instaLink.text.toString(),
                            binding.faceLink.toString(),
                            binding.whatLink.toString(),
                            binding.snapLink.text.toString(),
                            getLatLong().first,
                            getLatLong().second,
                            binding.description.text.toString(),
                            binding.phone.text.toString()
                        ),
                        intent.getStringExtra("token").toString(),
                        prepareFilePart(
                            "image", mFileUtils(this).getPath(it.toUri(), this),
                        )
                    )
                }

            } ?: let {
                Toast.makeText(this, "اختار صوره ", Toast.LENGTH_LONG).show()
            }


        }


    }
}