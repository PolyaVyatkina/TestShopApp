package com.molkos.testshopapp.models.displayableitem

data class CategoryList(
    override val id: Long = 1,
    val list: List<Category>
) : DisplayableItem

val defaultCategoryList = CategoryList(list = listOf(
    Category(id = 0, title = CategoryTitle.PHONES, isSelected = true),
    Category(id = 1, title = CategoryTitle.COMPUTER, isSelected = false),
    Category(id = 2, title = CategoryTitle.HEALTH, isSelected = false),
    Category(id = 3, title = CategoryTitle.BOOKS, isSelected = false),
    Category(id = 4, title = CategoryTitle.TOOLS, isSelected = false)
))