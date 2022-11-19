package com.molkos.testshopapp.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.molkos.testshopapp.R
import com.molkos.testshopapp.databinding.FragmentMainBinding
import com.molkos.testshopapp.glide.GlideApp
import com.molkos.testshopapp.models.UiState
import com.molkos.testshopapp.models.displayableitem.BestSeller
import com.molkos.testshopapp.models.displayableitem.BestSellersList
import com.molkos.testshopapp.models.displayableitem.HotSale
import com.molkos.testshopapp.models.displayableitem.HotSalesList
import com.molkos.testshopapp.models.toItemsList
import com.molkos.testshopapp.presentation.CommonViewModel
import com.molkos.testshopapp.presentation.cart.CartFragment
import com.molkos.testshopapp.presentation.details.ProductDetailsFragment
import com.molkos.testshopapp.presentation.main.filter.FilterOptionsFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModel<MainViewModel>()

    private val commonViewModel by viewModel<CommonViewModel>()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        MainAdapter(
            glide = GlideApp.with(this),
            scrollStates = viewModel.scrollStates,
            onCategoryClick = viewModel.onCategoryClick,
            onBestSellerClick = onBestSellerClick,
            onFavIconClick = onFavIconClick,
            onFilterClick = onFilterIconClick
        )
    }

    private val onBestSellerClick: (item: BestSeller) -> Unit = {
        openFragment(ProductDetailsFragment.newInstance())
    }

    private val onFavIconClick: (item: BestSeller) -> Unit = {
        val position = adapter.parts.bestSellers.id.toInt()
        adapter.notifyItemChanged(position, MainAdapter.Payload.BestSellerFavChanged(it))
    }

    private val onFilterIconClick: () -> Unit = {
        openFragment(FilterOptionsFragment.newInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectDataFromViewModel()

        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter

        binding.bottomBar.cart.setOnClickListener { openFragment(CartFragment.newInstance()) }
    }

    override fun onStop() {
        (binding.rv.layoutManager as? LinearLayoutManager)?.let { lm ->
            val visibleRange = lm.findFirstVisibleItemPosition()..lm.findLastVisibleItemPosition()
            for (i in visibleRange) {
                val state = binding.rv.findViewHolderForAdapterPosition(i)
                    ?.itemView
                    ?.findViewById<RecyclerView>(R.id.rv)
                    ?.layoutManager
                    ?.onSaveInstanceState()
                state?.let { viewModel.scrollStates[i] = it }
            }
        }
        super.onStop()
    }


    private fun collectDataFromViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hotSales.collect {
                    when (it) {
                        UiState.Loading -> {}
                        is UiState.Success -> {
                            val hotSales = it.data as List<HotSale>
                            adapter.apply {
                                parts.hotSales = HotSalesList(list = hotSales)
                                items = parts.toItemsList()
                                val position = adapter.parts.hotSales.id.toInt()
                                notifyItemChanged(position)
                            }
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bestSellers.collect {
                    when (it) {
                        UiState.Loading -> {}
                        is UiState.Success -> {
                            val bestSellers = it.data as List<BestSeller>
                            adapter.apply {
                                parts.bestSellers = BestSellersList(list = bestSellers)
                                items = parts.toItemsList()
                                val position = adapter.parts.bestSellers.id.toInt()
                                notifyItemChanged(position)
                            }
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect {
                    adapter.apply {
                        parts.categories = it
                        items = parts.toItemsList()
                        val position = adapter.parts.categories.id.toInt()
                        notifyItemChanged(position, MainAdapter.Payload.SelectedCategoryChanged)
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                commonViewModel.amountOfItemsInCart.collect { amount ->
                    if (amount == 0)
                        binding.bottomBar.amount.visibility = View.INVISIBLE
                    else {
                        binding.bottomBar.amount.visibility = View.VISIBLE
                        binding.bottomBar.amount.text = amount.toString()
                    }
                }
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(R.id.container, fragment)
            .commit()
    }
}