package com.molkos.testshopapp.models

import com.molkos.testshopapp.models.displayableitem.*

data class MainScreenParts(
    var locationAndFilter: LocationAndFilter = LocationAndFilter(location = "Zihuatanejo, Gro"),
    var categories: CategoryList = defaultCategoryList,
    val search: Search = Search,
    var hotSales: HotSalesList = HotSalesList(list = emptyList()),
    var bestSellers: BestSellersList = BestSellersList(list = emptyList()),
)

fun MainScreenParts.toItemsList(): List<DisplayableItem> = emptyList<DisplayableItem>() +
        locationAndFilter +
        categories +
        search +
        hotSales +
        bestSellers
