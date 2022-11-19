package com.molkos.testshopapp.domain.models

data class ProductInCart(
    val title: String,
    val pricePerOne: Int,
    val imageUrl: String,
    var count: Int,
)
