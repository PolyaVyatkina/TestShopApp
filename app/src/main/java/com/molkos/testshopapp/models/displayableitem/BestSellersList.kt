package com.molkos.testshopapp.models.displayableitem

data class BestSellersList(
    override val id: Long = 4,
    val list: List<BestSeller>
) : DisplayableItem