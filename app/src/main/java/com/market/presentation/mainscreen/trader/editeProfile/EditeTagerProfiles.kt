package com.market.presentation.mainscreen.trader.editeProfile

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUriUtils
import com.market.R
import com.market.data.models.SendCompleteJoin
import com.market.data.models.get.categories.Categories
import com.market.data.models.get.categories.Category
import com.market.data.models.get.tagerprofile.Merchant
import com.market.databinding.ActivityEditeProductBinding
import com.market.databinding.ActivityEditeTagerProfilesBinding
import com.market.presentation.authentication.trader.create.tagercompletedata.TagerCompleteViewModel
import com.market.presentation.bases.BaseActivity
import com.market.presentation.location.MapsActivity
import com.market.presentation.mainscreen.trader.showMyProfile.ShowMyTagerProfile
import com.market.utils.ResultState
import com.market.utils.prepareFilePart
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditeTagerProfiles : BaseActivity() {


    lateinit var binding: ActivityEditeTagerProfilesBinding

    val viewModel: TagerCompleteViewModel by viewModels()
    lateinit var listCat: ArrayList<Category>
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
                Toast.makeText(this, "اختار صوره ", Toast.LENGTH_LONG).show()
            }


        }


    }
    companion object{
        fun startTagerProfile(productId: Merchant?, context: Context) {
            val intent = Intent(context,EditeTagerProfiles::class.java)
            intent.putExtra("Merchant", productId)
            context.startActivity(intent)
        }
    }
}