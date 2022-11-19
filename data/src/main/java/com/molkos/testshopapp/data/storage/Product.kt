package com.molkos.testshopapp.data.storage

@kotlinx.serialization.Serializable
data class Product(
    val title: String,
    val price: Int,
    val imageUrl: String,
    var count: Int,
)
