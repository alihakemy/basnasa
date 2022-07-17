package com.market.presentation.mainscreen.user.ui.home.sliderFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.market.R
import com.market.presentation.mainscreen.user.displayproduct.DisplayProduct
import com.market.presentation.mainscreen.user.displaytrader.TraderProfileActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"
/**
 * A simple [Fragment] subclass.
 * Use the [UserSliderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserSliderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getString(ARG_PARAM4)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_user_slider, container, false)

        val  imageView10=view.findViewById<ImageView>(R.id.imageView10)

        Glide.with(requireContext()).load(param1).into(imageView10)
        imageView10.setOnClickListener {
            if (param3.toString().equals("product")) {
                DisplayProduct.startDisplayProduct(param4.toString(), requireContext())

            } else if (param3.toString().equals("tager")) {
                TraderProfileActivity.startTagerProfile(param4.toString(), requireContext())
            } else {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(param4)))

            }

        }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserSliderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String,typeDirection: String, showNumber: String) =
            UserSliderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, typeDirection)
                    putString(ARG_PARAM4, showNumber)
                }
            }
    }


}