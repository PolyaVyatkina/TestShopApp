package com.molkos.testshopapp.models.displayableitem

data class BestSeller(
    override val id: Long,
    val imageURL: String,
    val isFav: Boolean,
    val discountPrice: Int,
    val fullPrice: Int,
    val title: String
) : DisplayableItem