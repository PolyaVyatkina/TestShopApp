package com.molkos.testshopapp.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.molkos.testshopapp.R
import com.molkos.testshopapp.databinding.FragmentCartBinding
import com.molkos.testshopapp.domain.models.ProductInCart
import com.molkos.testshopapp.glide.GlideApp
import com.molkos.testshopapp.models.ItemState
import com.molkos.testshopapp.presentation.CommonViewModel
import com.molkos.testshopapp.presentation.adapters.CartAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<CartViewModel>()

    private val commonViewModel by activityViewModel<CommonViewModel>()

    private val adapter by lazy {
        CartAdapter(
            glide = GlideApp.with(requireContext()),
            onDeleteClick = onDeleteClick,
            onPlusClick = viewModel.onPlusClick,
            onMinusClick = viewModel.onMinusClick
        )
    }

    private val onDeleteClick: (item: ProductInCart) -> Unit = {
        viewModel.onDeleteClick.invoke(it)
        commonViewModel.refreshAmountOfItems()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener { requireActivity().onBackPressed() }
        binding.rv.adapter = adapter

        collectDataFromViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshItems()
    }

    private fun collectDataFromViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collect { list ->
                    adapter.setItems(list)
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemState.collect {
                    when (it) {
                        is ItemState.Changed -> adapter.notifyItemChanged(it.position,
                            CartAdapter.Payload.COUNT_CHANGED)
                        is ItemState.Removed -> adapter.notifyItemRemoved(it.position)
                        ItemState.Default -> {}
                    }
                    viewModel.resetItemState()
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemsCommonPrice.collect {
                    binding.totalPrice.text = requireContext().getString(R.string.cart_common_price).format(it)
                }
            }
        }
    }
}