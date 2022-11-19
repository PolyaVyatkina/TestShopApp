package com.molkos.testshopapp.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import com.jama.carouselview.enums.OffsetType
import com.molkos.testshopapp.R
import com.molkos.testshopapp.databinding.FragmentProductDetailsBinding
import com.molkos.testshopapp.domain.models.ProductDetails
import com.molkos.testshopapp.domain.models.ProductDetailsImage
import com.molkos.testshopapp.glide.GlideApp
import com.molkos.testshopapp.models.UiState
import com.molkos.testshopapp.presentation.adapters.ProductDetailsAdapter
import com.molkos.testshopapp.presentation.cart.CartFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ProductDetailsFragment()
    }

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ProductDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectDataFromViewModel()

        with(binding) {
            back.setOnClickListener { requireActivity().onBackPressed() }
            cart.setOnClickListener { openCartFragment() }

            detailsViewPager.adapter = ProductDetailsAdapter(this@ProductDetailsFragment)
            TabLayoutMediator(tabLayout, detailsViewPager) { tab, position ->
                tab.text = ProductDetailsAdapter.pages[position]
            }.attach()
        }
    }

    private fun collectDataFromViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productDetails.collect {
                    when (it) {
                        UiState.Loading -> {}
                        is UiState.Success -> {
                            val details = it.data as ProductDetails
                            bindCarouselView(details.images)
                        }
                    }
                }
            }
        }
    }

    private fun bindCarouselView(images: List<ProductDetailsImage>) {
        binding.carousel.apply {
            size = images.size
            resource = R.layout.item_product
            autoPlay = false
            carouselOffset = OffsetType.CENTER
            scaleOnScroll = true
            hideIndicator(true)
            setCarouselViewListener { view, position ->
                val imageView = view.findViewById<ImageView>(R.id.image)
                GlideApp.with(this@ProductDetailsFragment)
                    .load(images[position].imageUrl)
                    .placeholder(R.drawable.placeholder_product)
                    .into(imageView)
            }
            show()
        }
    }

    private fun openCartFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(R.id.container, CartFragment.newInstance())
            .commit()
    }
}