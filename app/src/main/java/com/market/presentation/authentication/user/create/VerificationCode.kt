package com.market.presentation.authentication.user.create

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.market.utils.ResultState
import com.market.data.models.SendVerificationPhone
import com.market.data.models.get.verificationPhone.VerificationPhone
import com.market.databinding.VerificationCodeFragmentBinding
import com.market.presentation.location.MapsActivity
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VerificationCode.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class VerificationCode : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: VerificationCodeFragmentBinding? = null

    val viewModel: CreateAccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = VerificationCodeFragmentBinding.inflate(inflater, container, false)







        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pd = ProgressDialog(context)
        pd.setMessage("loading")
        pd.setCancelable(false)
        binding?.textView11?.setOnClickListener {
            activity?.let {
                // it.onBackPressed()
            }
        }


        val phone = arguments?.getString("phone")

        binding?.textView10?.text=phone.toString()

        binding?.button?.setOnClickListener {

            pd.show()
            viewModel.verificationPhone(
                SendVerificationPhone(
                    phone.toString(),
                    binding?.otpView?.otp.toString()
                )
            ).observe(viewLifecycleOwner, Observer {

                when (val result = it) {

                    is ResultState.Success<VerificationPhone> -> {
                        if (pd.isShowing) {
                            pd.dismiss()
                        }
                        viewModel.storeLogin(result.data.data.user)
                        val intent = Intent(getContext(), MapsActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        activity?.let { it1 -> finishAffinity(it1) }

                    }
                    else -> {
                        if (pd.isShowing) {
                            pd.dismiss()
                        }
                    }

                }


            })


        }


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VerificationCode.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VerificationCode().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}