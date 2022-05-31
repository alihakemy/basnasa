package com.market.presentation.authentication.user.forget

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.market.R
import com.market.base.utils.ResultState
import com.market.data.models.ConfirmNewPassword
import com.market.data.models.get.getconfirenewpassword.GetConfirmNewPassword
import com.market.databinding.NewPasswordActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordStep3 : AppCompatActivity() {
  lateinit var binding:NewPasswordActivityBinding
  val viewModel :ResetPasswordViewModel by viewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=NewPasswordActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntent().getStringExtra("phone")?.let { Log.e("FinalStep", it) }
        val pd = ProgressDialog(this)
        pd.setMessage("loading")

        pd.setCancelable(false)
        binding.button.setOnClickListener {
            if(binding.passwordText.text.toString().equals(binding.passwordText2.text.toString())&&
                ( !binding.passwordText.text.isNullOrEmpty())&& ( !binding.passwordText2.text.isNullOrEmpty())){

                pd.show()
                viewModel.confirmNewPassword(ConfirmNewPassword(getIntent().getStringExtra("code").toString(),
                binding.passwordText.text.toString(),binding.passwordText.text.toString())).observe(this,
                Observer {

                    when(val result = it){
                        is ResultState.Success<GetConfirmNewPassword> ->{

                            Toast.makeText(
                                this,
                               "تم بنجاح",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }
                        else ->{
                            if (pd.isShowing) {
                                pd.dismiss()
                            }
                            Toast.makeText(
                                this,
                                it.toString(),
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }


                })


            }

        }
    }
}