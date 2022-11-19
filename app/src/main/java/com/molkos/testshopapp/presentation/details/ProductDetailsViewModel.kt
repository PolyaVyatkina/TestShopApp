package com.molkos.testshopapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molkos.testshopapp.domain.models.ProductDetails
import com.molkos.testshopapp.domain.models.Response
import com.molkos.testshopapp.domain.usecases.productdetails.AddProductToCartUseCase
import com.molkos.testshopapp.domain.usecases.productdetails.GetProductDetailsUseCase
import com.molkos.testshopapp.models.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    getProductDetailsUseCase: GetProductDetailsUseCase,
    addProductToCartUseCase: AddProductToCartUseCase,
) : ViewModel() {

    private val _productDetails: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val productDetails: StateFlow<UiState> = _productDetails

    private val _selectedColor: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedColor: StateFlow<Int> = _selectedColor

    private val _selectedCapacity: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedCapacity: StateFlow<Int> = _selectedCapacity

    val onColorClicked: (position: Int) -> Unit = { pos ->
        _selectedColor.value = pos
    }

    val onCapacityClick: (position: Int) -> Unit = { pos ->
        _selectedCapacity.value = pos
    }

    val onAddToCartClick: () -> Boolean = {
        val details = _productDetails.value
        if (details is UiState.Success) {
            val result = addProductToCartUseCase.execute(details.data as ProductDetails)
            result
        } else false
    }

    init {
        viewModelScope.launch {
            getProductDetailsUseCase.execute().collect { response ->
                _productDetails.value = when (response) {
                    is Response.Error -> UiState.Loading
                    is Response.Success -> UiState.Success(response.body as ProductDetails)
                }
            }
        }
    }
}