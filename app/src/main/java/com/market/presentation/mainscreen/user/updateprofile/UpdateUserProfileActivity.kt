package com.market.presentation.mainscreen.user.updateprofile

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.market.data.models.get.login.LoginResponse
import com.market.databinding.UpdateUserProfileActivityBinding
import com.market.presentation.authentication.forget.ForgetPasswordStep1
import com.market.presentation.authentication.forget.ForgetPasswordStep2
import com.market.presentation.authentication.user.login.LoginUser
import com.market.presentation.bases.BaseActivity
import com.market.presentation.mainscreen.notification.NotificationActivity
import com.market.presentation.mainscreen.user.changepassword.ChangeUserPasswordActivity
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateUserProfileActivity : BaseActivity() {
    lateinit var binding: UpdateUserProfileActivityBinding
    val viewModel: UpdateUserProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UpdateUserProfileActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pd = ProgressDialog(this)
        pd.setMessage("loading")

        pd.setCancelable(false)

        binding.editTextTextPersonName2.setText(getLoginData().data.user.name)
        binding.editTextTextPersonName.setText(getLoginData().data.user.phone)
        binding.editTextTextPersonName3.setText(getLoginData().data.user.email)

        binding.imageView52.setOnClickListener {
            viewModel.logOut()
        }
        binding.textView72.setOnClickListener {
            viewModel.logOut()
        }

        //updateProfile
        binding.button.setOnClickListener {
            if(!pd.isShowing){
                pd.show()
            }


            viewModel.updateUserProfile(
                userName = binding.editTextTextPersonName2.text.toString(),
                phone = binding.editTextTextPersonName.text.toString(),
                email = binding.editTextTextPersonName3.text.toString()
            )

        }

        viewModel.result.observe(this, Observer {

            when (val results = it) {
                is ResultState.Success<LoginResponse> -> {
                 viewModel.storeLogin(results.data)

                }
                else -> {

                    Toast.makeText(this,results.message.toString(),Toast.LENGTH_LONG).show()
                }
            }

            if(pd.isShowing){
                pd.dismiss()
            }
            onBackPressed()

        })


        binding.changePassword.setOnClickListener {
//            val intent: Intent = Intent(this, ChangeUserPasswordActivity::class.java)
//           startActivity(intent)
            val intent: Intent = Intent(this, ForgetPasswordStep1::class.java)
            startActivity(intent)
        }


    }

    companion object {
        fun startUpdateUserProfile(context: Context) {
            if (NotificationActivity.checkIsLogin(context)) {
                val intent: Intent = Intent(context, UpdateUserProfileActivity::class.java)
                context.startActivity(intent)

            } else {

                val intent = Intent(context, LoginUser::class.java)
                context.startActivity(intent)

            }

        }
    }
}