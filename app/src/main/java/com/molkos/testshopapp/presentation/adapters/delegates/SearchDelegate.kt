package com.molkos.testshopapp.presentation.adapters.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.molkos.testshopapp.databinding.SectionSearchBinding
import com.molkos.testshopapp.models.displayableitem.DisplayableItem
import com.molkos.testshopapp.models.displayableitem.Search


fun searchDelegate() = adapterDelegateViewBinding<Search, DisplayableItem, SectionSearchBinding>(
    { layoutInflater, container -> SectionSearchBinding.inflate(layoutInflater, container, false) }
) {
    bind { diffPayloads ->
    }

}