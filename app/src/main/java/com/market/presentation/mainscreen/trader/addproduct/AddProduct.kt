package com.market.presentation.mainscreen.trader.addproduct

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUriUtils
import com.market.data.models.get.addComment.DefaultResponse
import com.market.data.models.get.categories.Categories
import com.market.data.models.get.categories.Category
import com.market.data.models.get.currency.Currency
import com.market.data.models.get.currency.PaymentMethod
import com.market.databinding.ActivityAddProductBinding
import com.market.presentation.mainscreen.trader.addproduct.adapter.ImageAdapter
import com.market.utils.ResultState
import com.market.utils.prepareFilePart
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody


@AndroidEntryPoint
class AddProduct : AppCompatActivity() {
    lateinit var binding: ActivityAddProductBinding
    val viewModel: AddProductViewModel by viewModels()
    private var img: ArrayList<String> = arrayListOf()

    val imagesList = ArrayList<MultipartBody.Part>()
    var imageMain: MultipartBody.Part? = null
    var listCat: ArrayList<Category> = ArrayList()

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data

                    imagesList.add(
                        prepareFilePart(
                            "file[]",
                            FileUriUtils.getRealPath(this, fileUri.toString().toUri()).toString(),
                        )
                    )

                    img.add(fileUri.toString())
                    renderImagesList(img)

                }
                ImagePicker.RESULT_ERROR -> {
                }
                else -> {
                }
            }
        }

    private val startForProfileImageResultSingleImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data

                    imageMain =
                        prepareFilePart(
                            "image",
                            FileUriUtils.getRealPath(this, fileUri.toString().toUri()).toString(),
                        )

                    binding.imageView69.setImageURI(fileUri)


                }
                ImagePicker.RESULT_ERROR -> {
                }
                else -> {
                }
            }
        }

    val currencyName :ArrayList<String> = ArrayList()
    val paymentMethod :ArrayList<PaymentMethod> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pd = ProgressDialog(this)
        pd.setMessage("loading")

        pd.setCancelable(false)

        viewModel.getCurrency()

        binding.imageView69.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->

                    startForProfileImageResultSingleImage.launch(intent)
                }
        }
        binding.imageView70.setOnClickListener {

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


        viewModel.currency.observe(this, Observer {

            when(val  result =it){
                is ResultState.Success<Currency> ->{

                    result.data?.data?.paymentMethod?.forEach {
                        it.name?.let { it1 -> currencyName.add(it1) }
                        paymentMethod.add(it)
                    }
                    val adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        currencyName
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.currency.adapter = adapter
                }
            }


        })

        viewModel.categories.observe(this, Observer {

            when (val result = it) {

                is ResultState.Success<Categories> -> {
                    val list: ArrayList<String> = ArrayList()
                    result.data?.data?.categories?.let { it1 -> listCat.addAll(it1) }
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
        binding.textView92.setOnClickListener {

            if (imageMain != null) {
                if (!imagesList.isNullOrEmpty()) {
                    if (binding.productName.text.isBlank()) {
                        Toast.makeText(this, "اسم المنتج", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    if (binding.textView91.text.isBlank()) {
                        Toast.makeText(this, "سعر المنتج", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    var categoriesId = 0
                    listCat.forEach {

                        if (it.name.equals(binding.cat.selectedItem.toString().toString())) {
                            categoriesId = it.id
                        }

                    }
                    imageMain?.let { it1 ->
                        var currencyId:String ="3"
                        paymentMethod.forEach {

                            if(binding.currency.selectedItem.toString().equals(it.name)){
                                currencyId=it.id.toString()
                            }

                        }

                        viewModel.addProduct(
                            list = imagesList,
                            it1,
                            category_id = categoriesId.toString(),
                            mainprice = binding.textView91.text.toString(),
                            discount = binding.discount.text.toString(),
                            stoke = binding.textView91.text.toString(),
                            about = binding.details.text.toString(),
                            name = binding.productName.text.toString(),
                            currecny = currencyId
                        )
                    }
                    if (!pd.isShowing) {
                        pd.show()

                    }

                } else {
                    Toast.makeText(this, "اضف صور", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "  اضف صور رئسيه", Toast.LENGTH_LONG).show()

            }


        }

        viewModel.results.observe(this, Observer {

            when (val result = it) {
                is ResultState.Success<DefaultResponse> -> {
                    if (pd.isShowing) {
                        pd.dismiss()
                    }
                    Toast.makeText(this, "تم بنجاح", Toast.LENGTH_LONG).show()

                    finish()
                }
                else -> {
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_LONG).show()

                }
            }

        })


    }

    private fun renderImagesList(arrayList: ArrayList<String>?) {


        var adapter: ImageAdapter = ImageAdapter(arrayList, this) { postion ->

            removeImageFromList(postion)


        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this).also {
            it.orientation = LinearLayoutManager.HORIZONTAL
        }
        binding.recyclerView.adapter = adapter

        binding.recyclerView.visibility = View.VISIBLE
        adapter.notifyDataSetChanged()


    }

    private fun removeImageFromList(postion: Int) {
        if (!imagesList.isNullOrEmpty()) {
            img.removeAt(postion)
            imagesList.removeAt(postion)
            renderImagesList(img)
        }

    }

}