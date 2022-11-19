package com.molkos.testshopapp.domain.usecases.main

import com.molkos.testshopapp.domain.models.FilterOptions

class GetAvailableFilterOptionsUseCase() {

    fun execute(products: BestSellers): FilterOptions {
        val brands = products.map { it.title.split(' ').first() }.toSet().toList()
        val prices = listOf(0, 10000)
        val sizes = listOf(4.5, 5.5)
        return FilterOptions(
            brand = brands,
            priceRange = Pair(prices.min(), prices.max()),
            sizeRange = Pair(sizes.min(), sizes.max())
        )
    }
}