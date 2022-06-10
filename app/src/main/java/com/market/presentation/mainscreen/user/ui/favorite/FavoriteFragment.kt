package com.market.presentation.mainscreen.user.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.market.data.models.get.fav.Favourites
import com.market.data.models.get.verificationPhone.VerificationPhone
import com.market.databinding.FragmentNotificationsBinding
import com.market.presentation.mainscreen.user.MainActivityUser
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
        binding.recyclerView2.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView2.adapter = adapter

        binding.imageView15.setOnClickListener {

            val isSwitched: Boolean = adapter.toggleItemViewType()
            binding.recyclerView2.layoutManager =
                if (isSwitched) GridLayoutManager(
                    requireContext(),
                    2
                ) else LinearLayoutManager(requireContext())


        }

        viewModel.getFav((requireActivity() as MainActivityUser).getLoginData().data.token)





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