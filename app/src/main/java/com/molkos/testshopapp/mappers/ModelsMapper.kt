package com.molkos.testshopapp.mappers

import com.molkos.testshopapp.domain.models.BestSellersProduct
import com.molkos.testshopapp.domain.models.HotSalesProduct
import com.molkos.testshopapp.models.displayableitem.BestSeller
import com.molkos.testshopapp.models.displayableitem.HotSale

fun HotSalesProduct.toDisplayableItem() =
    HotSale(
        id = this.id.toLong(),
        isNew = this.isNew ?: false,
        title = this.title,
        description = this.subtitle,
        imageURL = this.picture
    )


fun BestSellersProduct.toDisplayableItem() =
    BestSeller(
        id = this.id.toLong(),
        imageURL = this.picture,
        isFav = this.isFavorites,
        discountPrice = this.discountPrice,
        fullPrice = this.priceWithoutDiscount,
        title = this.title
    )
