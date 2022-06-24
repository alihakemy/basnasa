package com.market.presentation.mainscreen.notification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.market.data.models.get.notification.Data
import com.market.data.models.get.notification.NotificationModel
import com.market.databinding.ActivityNotificationBinding
import com.market.presentation.bases.BaseActivity
import com.market.presentation.mainscreen.notification.adapter.NotificationAdapter
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : BaseActivity() {
    lateinit var binding: ActivityNotificationBinding
    val viewModel: NotificationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.categories.observe(this, Observer {


            when (val result = it) {
                is ResultState.Success<NotificationModel> -> {


                    renderData(result?.data?.data)
                }
                else -> {
                    Toast.makeText(
                        this,
                        result.data?.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()

                }
            }

        })

    }

    fun renderData(data: Data?) {

        val adapter = NotificationAdapter(data?.data)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.rec.layoutManager=linearLayoutManager
        binding.rec.adapter=adapter

    }

    companion object {
        fun startNotification(context: Context) {

            val intent = Intent(context, NotificationActivity::class.java)
            context.startActivity(intent)


        }
    }
}