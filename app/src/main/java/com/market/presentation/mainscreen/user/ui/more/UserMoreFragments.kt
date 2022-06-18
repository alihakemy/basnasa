package com.market.presentation.mainscreen.user.ui.more

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.market.R
import com.market.databinding.FragmentUserMoreFragmentsBinding
import com.market.databinding.VerificationCodeFragmentBinding
import com.market.presentation.authentication.trader.create.tagercompletedata.CompleteTagerDataActivity
import com.market.presentation.authentication.trader.traderlogin.LoginAsTrader
import com.market.presentation.authentication.user.login.LoginUser
import com.market.presentation.location.MapsActivity
import com.market.presentation.mainscreen.user.MainActivityUser
import com.market.presentation.mainscreen.user.ui.offers.OffersActivity
import com.market.utils.startLink
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserMoreFragments.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserMoreFragments : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentUserMoreFragmentsBinding
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
        binding = FragmentUserMoreFragmentsBinding.inflate(inflater, container, false)

        var coder = Geocoder(requireContext())

        val activitys = (activity as MainActivityUser)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                val addressList: List<Address> = coder.getFromLocation(
                    activitys.getLatLong().first.toDouble(),
                    activitys.getLatLong().second.toDouble(),
                    1
                )
                if (!addressList.isNullOrEmpty()) {
                    val location: Address = addressList[0]
                    binding.textView39.text = location.countryName.toString()
                }
            }

        }


        binding.linearLayout12.setOnClickListener {

            val intent = Intent(requireContext(), MapsActivity::class.java)
            intent.putExtra("role", "location")

            startActivity(intent)


        }



        binding.textView36.setOnClickListener {

            val intent = Intent(requireContext(), LoginAsTrader::class.java)
            startActivity(intent)
        }

        binding.account.setOnClickListener {
            val intent = Intent(requireContext(), LoginUser::class.java)
            startActivity(intent)

        }
        binding.join.setOnClickListener {

            val intent = Intent(requireContext(), CompleteTagerDataActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("token", activitys.getLoginData().data.token)
            startActivity(intent)

        }
        binding.offers.setOnClickListener {
            val intent = Intent(requireContext(), OffersActivity::class.java)

            startActivity(intent)

        }

        binding.shareAPP.setOnClickListener {
            startLink(
                "https://play.google.com/store/apps/details?id=" + "com.market",
                requireContext()
            )

        }



        return binding?.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserMoreFragments.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserMoreFragments().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}