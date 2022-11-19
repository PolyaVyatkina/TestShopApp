package com.molkos.testshopapp.presentation.cart

import androidx.lifecycle.ViewModel
import com.molkos.testshopapp.domain.models.ProductInCart
import com.molkos.testshopapp.domain.usecases.cart.ChangeProductCountUseCase
import com.molkos.testshopapp.domain.usecases.cart.GetProductsInCartUseCase
import com.molkos.testshopapp.domain.usecases.cart.RemoveProductFromCartUseCase
import com.molkos.testshopapp.models.ItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel(
    private val getProductsInCartUseCase: GetProductsInCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
    private val changeProductCountUseCase: ChangeProductCountUseCase,
) : ViewModel() {

    private val _items: MutableStateFlow<List<ProductInCart>> = MutableStateFlow(listOf())
    val items: StateFlow<List<ProductInCart>> = _items

    private val _itemState: MutableStateFlow<ItemState> = MutableStateFlow(ItemState.Default)
    val itemState: StateFlow<ItemState> = _itemState

    private val _itemsCommonPrice: MutableStateFlow<Int> = MutableStateFlow(0)
    val itemsCommonPrice: StateFlow<Int> = _itemsCommonPrice

    val onDeleteClick: (item: ProductInCart) -> Unit = { item ->
        val res = removeProductFromCartUseCase.execute(item)
        if (res) {
            val itemIndex = _items.value.indexOf(item)
            if (itemIndex >= 0) {
                _items.value -= item
                _itemState.value = ItemState.Removed(itemIndex)
            }
        }
    }
    val onPlusClick: (item: ProductInCart) -> Unit = { item ->
        val res = changeProductCountUseCase.execute(item, item.count + 1)
        if (res) {
            val itemIndex = _items.value.indexOf(item)
            if (itemIndex >= 0) _items.value[itemIndex].count++
            _itemState.value = ItemState.Changed(itemIndex)
            _itemsCommonPrice.value = computeCommonPrice()
        }
    }
    val onMinusClick: (item: ProductInCart) -> Unit = { item ->
        val res = changeProductCountUseCase.execute(item, item.count - 1)
        if (res) {
            val itemIndex = _items.value.indexOf(item)
            if (itemIndex >= 0)
                _items.value[itemIndex].count--
            _itemState.value = ItemState.Changed(itemIndex)
            _itemsCommonPrice.value = computeCommonPrice()
        }
    }

    init {
        refreshItems()
    }

    fun refreshItems() {
        val products = getProductsInCartUseCase.execute()
        _items.value = products
        _itemsCommonPrice.value = computeCommonPrice()
    }

    fun resetItemState() {
        _itemState.value = ItemState.Default
    }

    private fun computeCommonPrice(): Int {
        val list = _items.value
        var price = 0
        list.forEach {
            price += it.pricePerOne * it.count
        }
        return price
    }
}