package com.molkos.testshopapp.domain.usecases.cart

import com.molkos.testshopapp.domain.models.ProductInCart
import com.molkos.testshopapp.domain.repositories.CartRepository

class RemoveProductFromCartUseCase(private val cartRepository: CartRepository) {

    fun execute(product: ProductInCart): Boolean {
        return cartRepository.removeProduct(product)
    }

}