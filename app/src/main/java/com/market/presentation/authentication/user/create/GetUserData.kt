package com.market.presentation.authentication.user.create

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.market.R
import com.market.data.models.SendRegister
import com.market.data.models.get.register.RegisterResponse
import com.market.databinding.GetUserDataFragmentBinding
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GetUserData.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class GetUserData : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val bundle = Bundle()
    val viewModel: CreateAccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var binding: GetUserDataFragmentBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GetUserDataFragmentBinding.inflate(inflater, container, false)




        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pd = ProgressDialog(context)
        pd.setMessage("loading")

        pd.setCancelable(false)



        binding?.imageView3?.setOnClickListener {
            activity?.onBackPressed()
        }
        binding?.button?.setOnClickListener {
            bundle.putString("userName", binding?.editTextTextPersonName2?.text.toString())
            bundle.putString("phone", binding?.editTextTextPersonName?.text.toString())
            bundle.putString("email", binding?.editTextTextPersonName3?.text.toString())
            bundle.putString("password", binding?.editTextTextPersonName4?.text.toString())
            bundle.putString("role", "clients")

            pd.show()
            viewModel.registerUser(
                SendRegister(
                    name =
                    binding?.editTextTextPersonName2?.text.toString(),
                    password = binding?.editTextTextPersonName4?.text.toString(),
                    phone = binding?.editTextTextPersonName?.text.toString(),
                    email = binding?.editTextTextPersonName3?.text.toString(),
                    role = "clients", Installed = "0"
                )
            ).observe(viewLifecycleOwner, Observer {

                when (val result = it) {
                    is ResultState.Success<RegisterResponse> -> {
                        if (pd.isShowing) {
                            pd.dismiss()
                        }
                        findNavController().navigate(
                            R.id.action_getUserData_to_verificationCode,
                            bundle
                        )

                        Toast.makeText(
                            context,
                            result.data!!.data.activation_code.toString(),
                            Toast.LENGTH_LONG
                        ).show()


                    }
                    else -> {
                        Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show()

                        if (pd.isShowing) {
                            pd.dismiss()
                        }

                    }
                }


            })


        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GetUserData.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GetUserData().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}