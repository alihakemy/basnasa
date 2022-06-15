package com.market.presentation.mainscreen.user.ui.favorite

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.market.data.models.get.fav.Favourites
import com.market.data.models.get.verificationPhone.VerificationPhone
import com.market.databinding.FragmentNotificationsBinding
import com.market.presentation.location.MapsActivity
import com.market.presentation.mainscreen.user.MainActivityUser
import com.market.presentation.mainscreen.user.search.SearchActivity
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val viewModel: FavProductViewModel by viewModels()

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val adapter = UserFavAdapter()
        binding.merchants.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.merchants.adapter = adapter



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
        binding.imageView31.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }





        binding.imageView32.setOnClickListener {

            val isSwitched: Boolean = adapter.toggleItemViewType()
            binding.merchants.layoutManager =
                if (isSwitched) GridLayoutManager(
                    requireContext(),
                    2
                ) else LinearLayoutManager(requireContext())


        }


        viewModel.getFav((requireActivity() as MainActivityUser).getLoginData().data.token,
        activitys.getLatLong().first,activitys.getLatLong().second)





        viewModel.results.observe(viewLifecycleOwner, Observer {
            when (val result = it) {
                is ResultState.Success<Favourites> -> {

                    Log.e("ALISAMI", result.data?.data.toString())
                    result.data?.data?.let { it1 -> adapter.setList(it1) }
                    adapter.notifyDataSetChanged()

                }
                else -> {
                    Log.e("ALISAMI", result.data?.data.toString())

                }

            }


        })




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}