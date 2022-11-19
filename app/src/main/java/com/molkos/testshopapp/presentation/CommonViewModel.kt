package com.molkos.testshopapp.presentation

import androidx.lifecycle.ViewModel
import com.molkos.testshopapp.domain.usecases.main.GetAmountOfItemsInCart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CommonViewModel(
    private val getAmountOfItemsInCart: GetAmountOfItemsInCart
) : ViewModel() {

    private val _amountOfItemsInCart: MutableStateFlow<Int> = MutableStateFlow(0)
    val amountOfItemsInCart: StateFlow<Int> = _amountOfItemsInCart

    init {
        refreshAmountOfItems()
    }

    fun refreshAmountOfItems() {
        _amountOfItemsInCart.value = getAmountOfItemsInCart.execute()
    }
}