package com.market.presentation.mainscreen.trader.editeProfile

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUriUtils
import com.market.data.models.SendCompleteJoin
import com.market.data.models.get.categories.Categories
import com.market.data.models.get.categories.Category
import com.market.data.models.get.tagerprofile.Merchant
import com.market.databinding.ActivityEditeTagerProfilesBinding
import com.market.presentation.authentication.trader.create.tagercompletedata.TagerCompleteViewModel
import com.market.presentation.bases.BaseActivity
import com.market.presentation.location.MapsActivity
import com.market.utils.ResultState
import com.market.utils.prepareFilePart
import com.zeeshan.material.multiselectionspinner.MultiSelectionSpinner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditeTagerProfiles : BaseActivity() {


    lateinit var binding: ActivityEditeTagerProfilesBinding

    val viewModel: TagerCompleteViewModel by viewModels()
    lateinit var listCat: ArrayList<Category>
    var selected: ArrayList<Category> = ArrayList()

    var imageUrl: Uri? = null
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data

                    imageUrl = fileUri
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
        binding = ActivityEditeTagerProfilesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pd = ProgressDialog(this)
        pd.setMessage("loading")
        pd.setCancelable(false)

        val merchant=intent.getParcelableExtra<Merchant>("Merchant")



        binding.editTextTextPersonName2.setText(getLoginData().data.user.name)
        binding.editTextTextPersonName3.setText(getLoginData().data.user.email)
        binding.editTextTextPersonName.setText(getLoginData().data.user.shop_name)

        Glide.with(this).load(merchant?.imagePath.toString())
            .into(binding.imageView7)


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


                    val itemsNames = ArrayList<String>()
                    listCat.forEach {
                        itemsNames.add(it.name)
                    }
                    binding.cat.items = itemsNames as List<Any>?
                    binding.cat.setOnItemSelectedListener(object :
                        MultiSelectionSpinner.OnItemSelectedListener {
                        override fun onItemSelected(
                            view: View,
                            isSelected: Boolean,
                            position: Int
                        ) {
                            if (isSelected) {
                                selected.add(listCat.get(position))

                            } else {
                            //    selected.removeAt(position)
                            }

                        }

                        override fun onSelectionCleared() {}
                    })

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


            if(selected.isNullOrEmpty()){
                Toast.makeText(this, "اكمل بياناتك ", Toast.LENGTH_LONG).show()

                return@setOnClickListener
            }

            imageUrl?.let {
                if (getLatLong().first.equals("0")) {
                    Toast.makeText(this, "اكمل بيانتاتك ", Toast.LENGTH_LONG).show()

                } else {
                    pd.show()
                    val ids=ArrayList<Int>()
                    selected.forEach {
                        ids.add(it.id)
                    }
                    viewModel.uploadStore(
                        SendCompleteJoin(
                            ids,
                            arrivaltime = binding.arrivalTime.text.toString(),
                            binding.instaLink.text.toString(),
                            binding.faceLink.text.toString(),
                            binding.whatLink.text.toString(),
                            binding.snapLink.text.toString(),
                            getLatLong().first,
                            getLatLong().second,
                            binding.description.text.toString(),
                            binding.phone.text.toString()
                        ),
                        intent.getStringExtra("token").toString(),
                        prepareFilePart(
                            "image",
                            FileUriUtils.getRealPath(this, imageUrl.toString().toUri()).toString(),
                        )
                    )
                }

            } ?: let {
                pd.show()
                val ids=ArrayList<Int>()
                selected.forEach {
                    ids.add(it.id)
                }
                viewModel.uploadStore(
                    SendCompleteJoin(
                        ids,
                        arrivaltime = binding.arrivalTime.text.toString(),
                        binding.instaLink.text.toString(),
                        binding.faceLink.text.toString(),
                        binding.whatLink.text.toString(),
                        binding.snapLink.text.toString(),
                        getLatLong().first,
                        getLatLong().second,
                        binding.description.text.toString(),
                        binding.phone.text.toString()
                    ),
                    intent.getStringExtra("token").toString()
                )
            }


        }


    }
    companion object{
        fun startTagerProfile(merchantId: Merchant?, context: Context) {
            val intent = Intent(context,EditeTagerProfiles::class.java)
            intent.putExtra("Merchant", merchantId)
            context.startActivity(intent)
        }
    }
}