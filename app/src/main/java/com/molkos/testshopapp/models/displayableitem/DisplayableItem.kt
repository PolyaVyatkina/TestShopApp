package com.molkos.testshopapp.models.displayableitem

interface DisplayableItem {
    val id: Long
    override fun equals(other: Any?): Boolean
}