package com.molkos.testshopapp.presentation.details

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.molkos.testshopapp.R
import com.molkos.testshopapp.databinding.FragmentShopBinding
import com.molkos.testshopapp.domain.models.ProductDetails
import com.molkos.testshopapp.models.UiState
import com.molkos.testshopapp.presentation.CommonViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ShopFragment : Fragment() {

    companion object {
        fun newInstance() = ShopFragment()
    }

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModel<ProductDetailsViewModel>()

    private val commonViewModel by activityViewModel<CommonViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.add.setOnClickListener {
            val result = viewModel.onAddToCartClick.invoke()
            if (result) {
                commonViewModel.refreshAmountOfItems()
                Toast.makeText(requireContext(), getString(R.string.toast_added_to_cart), Toast.LENGTH_SHORT).show()
            }
        }

        collectDataFromViewModel()
    }

    private fun collectDataFromViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productDetails.collect {
                    when (it) {
                        UiState.Loading -> {}
                        is UiState.Success -> {
                            val details = it.data as ProductDetails
                            bindDetails(details)
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedColor.collect { position ->
                    with(binding) {
                        if (position == 0) {
                            colorViewOnSelected(color1)
                            colorViewOnUnselected(color2)
                        } else {
                            colorViewOnSelected(color2)
                            colorViewOnUnselected(color1)
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedCapacity.collect { position ->
                    with(binding) {
                        if (position == 0) {
                            capacityViewOnSelected(capacity1)
                            capacityViewOnUnselected(capacity2)
                        } else {
                            capacityViewOnSelected(capacity2)
                            capacityViewOnUnselected(capacity1)
                        }
                    }
                }
            }
        }
    }

    private fun bindDetails(details: ProductDetails) {
        with(binding) {
            price.text = requireContext().getString(R.string.product_price).format(details.price.toString())
            infoCamera.text = details.camera
            infoCpu.text = details.cpu
            infoSd.text = details.sd
            infoSsd.text = details.ssd

            capacity1.text = requireContext().getString(R.string.product_capacity).format(details.capacity[0])
            capacity2.text = requireContext().getString(R.string.product_capacity).format(details.capacity[1])
            capacity1.setOnClickListener { viewModel.onCapacityClick.invoke(0) }
            capacity2.setOnClickListener { viewModel.onCapacityClick.invoke(1) }

            color1.background.setColorFilter(Color.parseColor(details.colors[0]), PorterDuff.Mode.SRC_ATOP)
            color2.background.setColorFilter(Color.parseColor(details.colors[1]), PorterDuff.Mode.SRC_ATOP)
            color1.setOnClickListener { viewModel.onColorClicked.invoke(0) }
            color2.setOnClickListener { viewModel.onColorClicked.invoke(1) }
        }
    }

    private fun colorViewOnSelected(view: ImageView) {
        view.setImageResource(R.drawable.ic_check)
    }

    private fun colorViewOnUnselected(view: ImageView) {
        view.setImageResource(android.R.color.transparent)
    }

    private fun capacityViewOnSelected(view: TextView) {
        view.background = ContextCompat.getDrawable(requireContext(), R.drawable.rectangle_orange_rounded10)
        view.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    private fun capacityViewOnUnselected(view: TextView) {
        view.setBackgroundResource(android.R.color.transparent)
        view.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_4))
    }
}