package com.molkos.testshopapp.data.storage

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedPrefsProductStorage(context: Context) : ProductStorage {

    companion object {
        const val SHARED_PREFS_KEY = "com.molkos.testshopapp.SHARED_PREFS"
        const val PRODUCT_KEY = "com.molkos.testshopapp.PRODUCT"
    }

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)

    override fun get(): List<Product> {
        val productSetJson = sharedPreferences.getStringSet(PRODUCT_KEY, setOf()) ?: setOf<String>()
        if (productSetJson.toString() == "[{}]") return emptyList()
        return productSetJson.map { Json.decodeFromString(it) }
    }

    override fun add(product: Product): Boolean {
        val newProductList = this.get() + product
        val newProductSetJson = newProductList.map { Json.encodeToString(it) }.toSet()
        return sharedPreferences.edit().putStringSet(PRODUCT_KEY, newProductSetJson).commit()
    }

    override fun remove(product: Product): Boolean {
        val productList = this.get()
        return if (!productList.contains(product)) false
        else {
            val newProductList = productList - product
            val newProductSetJson = newProductList.map { Json.encodeToString(it) }.toSet()
            return sharedPreferences.edit().putStringSet(PRODUCT_KEY, newProductSetJson).commit()
        }
    }

    override fun changeCount(product: Product, count: Int): Boolean {
        val productList = this.get()
        productList.forEach {
            if (it == product) it.count = count
        }
        val newProductSetJson = productList.map { Json.encodeToString(it) }.toSet()
        return sharedPreferences.edit().putStringSet(PRODUCT_KEY, newProductSetJson).commit()
    }
}