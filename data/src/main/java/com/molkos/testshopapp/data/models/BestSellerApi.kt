package com.molkos.testshopapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BestSellerApi(
    @SerialName("id") val id: Int,
    @SerialName("is_favorites") val isFavorites: Boolean,
    @SerialName("title") val title: String,
    @SerialName("price_without_discount") val priceWithoutDiscount: Int,
    @SerialName("discount_price") val discountPrice: Int,
    @SerialName("picture") val picture: String,
)