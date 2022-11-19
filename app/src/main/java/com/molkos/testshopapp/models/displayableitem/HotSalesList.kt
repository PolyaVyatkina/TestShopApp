package com.molkos.testshopapp.models.displayableitem

data class HotSalesList(
    override val id: Long = 3,
    val list: List<HotSale>
) : DisplayableItem 