package com.molkos.testshopapp.domain.usecases.cart

import com.molkos.testshopapp.domain.models.ProductInCart
import com.molkos.testshopapp.domain.repositories.CartRepository

class ChangeProductCountUseCase(private val cartRepository: CartRepository) {

    fun execute(product: ProductInCart, count: Int): Boolean {
        return if (product.count == 1 && count == 0) false
        else cartRepository.changeProductCount(product, count)
    }

}