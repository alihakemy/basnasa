package com.market.presentation.mainscreen.user.search

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.market.data.models.get.search.SearchResults
import com.market.databinding.ActivitySearchBinding
import com.market.presentation.authentication.user.login.LoginUser
import com.market.presentation.bases.BaseActivity
import com.market.presentation.location.MapsActivity
import com.market.presentation.mainscreen.user.MainActivityUser
import com.market.presentation.mainscreen.user.search.adapter.SearchAdapter
import com.market.presentation.mainscreen.user.ui.home.merchants.UserMerchantsAdapter
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    lateinit var binding: ActivitySearchBinding

    val viewModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var coder = Geocoder(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                try {
                    val addressList: List<Address> = coder.getFromLocation(
                        getLatLong().first.toDouble(),
                        getLatLong().second.toDouble(),
                        1
                    )
                    if (!addressList.isNullOrEmpty()) {
                        val location: Address = addressList[0]
                        binding.textView39.text = location.countryName.toString()

                    }

                }catch (e:Exception){

                }

            }

        }
        binding.imageView22.setOnClickListener {
            onBackPressed()
        }

        binding.textView39.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("role", "location")

            startActivity(intent)
        }



        binding.searchText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.action === KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    // Perform action on key press
                    Log.e("Clicked", "ClikedALI")
                    viewModel.performSearch(
                        binding.searchText.text.toString(),
                        getLatLong().first.toString(), getLatLong().second.toString()
                    )
                    return true
                }
                return false
            }
        })
        viewModel.results.observe(this, Observer {
            when (val result = it) {

                is ResultState.Success<SearchResults> -> {

                    result.data?.let { it1 -> initAdapter(searchResults = it1) }
                }
                else -> {

                }

            }

        })


    }

    private fun initAdapter(searchResults: SearchResults) {

        binding.recyclerView3.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView3.adapter = SearchAdapter(searchResults,checkIsLogin()) { boolean, id ->


            if (boolean) {
                if (checkIsLogin()) {
                    viewModel.perFormLike(id)
                } else {

                    val intent = Intent(this@SearchActivity, LoginUser::class.java)
                    startActivity(intent)

                }

            } else {
                viewModel.perFormUnLike(id)

            }

        }
    }

}