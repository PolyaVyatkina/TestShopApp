package com.molkos.testshopapp.data.storage

interface ProductStorage {

    fun get(): List<Product>

    fun add(product: Product): Boolean

    fun remove(product: Product): Boolean

    fun changeCount(product: Product, count: Int): Boolean
}