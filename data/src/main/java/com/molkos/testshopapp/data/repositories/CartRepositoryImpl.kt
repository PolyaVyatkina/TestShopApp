package com.molkos.testshopapp.data.repositories

import com.molkos.testshopapp.data.mappers.toProduct
import com.molkos.testshopapp.data.mappers.toProductInCart
import com.molkos.testshopapp.data.storage.ProductStorage
import com.molkos.testshopapp.domain.models.ProductInCart
import com.molkos.testshopapp.domain.repositories.CartRepository

class CartRepositoryImpl(private val storage: ProductStorage) : CartRepository {

    override fun getProducts(): List<ProductInCart> =
        storage.get().map { it.toProductInCart() }

    override fun addProduct(item: ProductInCart): Boolean {
        val product = item.toProduct()
        for (element in storage.get()) {
            if (element.title == product.title)
                return storage.changeCount(product, product.count + 1)
        }
        return storage.add(product)
    }

    override fun removeProduct(item: ProductInCart): Boolean =
        storage.remove(item.toProduct())

    override fun changeProductCount(item: ProductInCart, count: Int): Boolean =
        storage.changeCount(item.toProduct(), count)
}