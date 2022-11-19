package com.molkos.testshopapp.domain.repositories

import com.molkos.testshopapp.domain.models.ProductInCart

interface CartRepository {

    fun getProducts(): List<ProductInCart>

    fun addProduct(item: ProductInCart): Boolean

    fun removeProduct(item: ProductInCart): Boolean

    fun changeProductCount(item: ProductInCart, count: Int): Boolean

}