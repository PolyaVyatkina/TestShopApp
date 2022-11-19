package com.molkos.testshopapp.domain.models

data class FilterOptions(
    val brand: List<String>,
    val priceRange: Pair<Int, Int>,
    val sizeRange: Pair<Double, Double>
) {
    constructor() : this(
        emptyList(),
        Pair(0, 0),
        Pair(0.0, 0.0)
    )
}
