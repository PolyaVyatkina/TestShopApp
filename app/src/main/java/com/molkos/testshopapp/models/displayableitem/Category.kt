package com.molkos.testshopapp.models.displayableitem

data class Category(
    val title: CategoryTitle,
    override val id: Long = title.ordinal.toLong(),
    val isSelected: Boolean
) : DisplayableItem