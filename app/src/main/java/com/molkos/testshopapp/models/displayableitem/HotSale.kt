package com.molkos.testshopapp.models.displayableitem

data class HotSale(
    override val id: Long,
    val isNew: Boolean,
    val title: String,
    val description: String,
    val imageURL: String
) : DisplayableItem