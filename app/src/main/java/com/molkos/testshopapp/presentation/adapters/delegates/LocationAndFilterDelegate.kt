package com.molkos.testshopapp.presentation.adapters.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.molkos.testshopapp.databinding.SectionLocationAndFilterBinding
import com.molkos.testshopapp.models.displayableitem.DisplayableItem
import com.molkos.testshopapp.models.displayableitem.LocationAndFilter


fun locationAndFilterDelegate(
    onFilterClick: () -> Unit,
) =
    adapterDelegateViewBinding<LocationAndFilter, DisplayableItem, SectionLocationAndFilterBinding>(
        { layoutInflater, container -> SectionLocationAndFilterBinding.inflate(layoutInflater, container, false) }
    ) {
        bind { diffPayloads ->
            binding.locationText.text = item.location
            binding.filterIcon.setOnClickListener { onFilterClick.invoke() }
        }

    }
