package com.molkos.testshopapp.domain.usecases.main

import com.molkos.testshopapp.domain.repositories.CartRepository

class GetAmountOfItemsInCart(private val cartRepository: CartRepository) {

    fun execute(): Int {
        return cartRepository.getProducts().size
    }
}