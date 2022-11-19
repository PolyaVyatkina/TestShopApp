package com.molkos.testshopapp.domain.usecases.cart

import com.molkos.testshopapp.domain.models.ProductInCart
import com.molkos.testshopapp.domain.repositories.CartRepository

class GetProductsInCartUseCase(private val cartRepository: CartRepository) {

    fun execute(): List<ProductInCart> {
        return cartRepository.getProducts()
    }

}