package com.molkos.testshopapp.domain.models

data class BestSellersProduct(
    val id: Int,
    val isFavorites: Boolean,
    val title: String,
    val priceWithoutDiscount: Int,
    val discountPrice: Int,
    val picture: String,
) : ResponseBody