package com.market.presentation.mainscreen.user.search

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.market.data.models.get.search.SearchResults
import com.market.databinding.ActivitySearchBinding
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding

    val viewModel :SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.getSearchText().observe(this, Observer {
            binding.searchText.setText(it)

        })

        binding.searchText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.action === KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    // Perform action on key press
                    viewModel.search(binding.searchText.text.toString())
                    return true
                }
                return false
            }
        })
        viewModel.results.observe(this, Observer {
            when(val result =it){

                is ResultState.Success<SearchResults> ->{

                }
                else ->{

                }

            }

        })


    }

    fun initAdapter(){

    }

}