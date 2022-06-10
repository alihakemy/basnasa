package com.market.presentation.mainscreen.user.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.market.databinding.FragmentNotificationsBinding

class FavoriteFragment : Fragment() {

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

        val adapter =UserFavAdapter()
        binding.recyclerView2.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView2.adapter = adapter

        binding.imageView15.setOnClickListener {

            val isSwitched: Boolean = adapter.toggleItemViewType()
            binding.recyclerView2.layoutManager =
                if (isSwitched)  GridLayoutManager(
                requireContext(),
                2
            )else LinearLayoutManager(requireContext())

            adapter.notifyDataSetChanged()

        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}