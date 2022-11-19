package com.molkos.testshopapp.models.displayableitem

object Search : DisplayableItem {
    override val id: Long = 2

    override fun equals(other: Any?): Boolean =
        other is Search
}
