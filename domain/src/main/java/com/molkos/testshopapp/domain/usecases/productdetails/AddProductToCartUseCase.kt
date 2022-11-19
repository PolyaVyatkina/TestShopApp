package com.molkos.testshopapp.domain.usecases.productdetails

import com.molkos.testshopapp.domain.models.ProductDetails
import com.molkos.testshopapp.domain.models.ProductInCart
import com.molkos.testshopapp.domain.repositories.CartRepository

class AddProductToCartUseCase(private val cartRepository: CartRepository) {

    fun execute(product: ProductDetails): Boolean {
        val productInCart = ProductInCart(
            title = product.title,
            imageUrl = product.images.first().imageUrl,
            pricePerOne = product.price,
            count = 1
        )
        return cartRepository.addProduct(productInCart)
    }
}