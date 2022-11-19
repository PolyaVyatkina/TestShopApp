package com.molkos.testshopapp.presentation.main.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.molkos.testshopapp.R
import com.molkos.testshopapp.databinding.FragmentFilterBinding
import com.molkos.testshopapp.presentation.main.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FilterOptionsFragment : Fragment() {

    companion object {
        fun newInstance() = FilterOptionsFragment()
    }

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancel.setOnClickListener { requireActivity().onBackPressed() }
        binding.done.setOnClickListener { requireActivity().onBackPressed() }

        val brandsAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.item_spinner)
        binding.brandSpinner.adapter = brandsAdapter

        val pricesAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.item_spinner)
        binding.priceSpinner.adapter = pricesAdapter

        val sizesAdapter: ArrayAdapter<Double> = ArrayAdapter(requireContext(), R.layout.item_spinner)
        binding.sizeSpinner.adapter = sizesAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.availableFilters.collect {
                    brandsAdapter.clear()
                    pricesAdapter.clear()
                    sizesAdapter.clear()
                    brandsAdapter.addAll(it.brand)
                    pricesAdapter.addAll(computePrices(it.priceRange.first, it.priceRange.second))
                    sizesAdapter.addAll(it.sizeRange.first, it.sizeRange.second)
                }
            }
        }
    }

    private fun computePrices(minPrice: Int, maxPrice: Int): List<String> {
        val prices = mutableListOf(minPrice)
        val step = 1000
        while (prices.last() < maxPrice)
            if (prices.last() + step <= maxPrice) prices.add(prices.last() + step)
            else break
        val resultList = mutableListOf<String>()
        for (i in 0 until prices.size - 1) {
            resultList.add("${prices[i]} – ${prices[i + 1]}")
        }
        return resultList
    }
}