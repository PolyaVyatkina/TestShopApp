package com.molkos.testshopapp.models.displayableitem

data class LocationAndFilter(
    override val id: Long = 0,
    val location: String
) : DisplayableItem