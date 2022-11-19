package com.molkos.testshopapp.data.mappers

import com.molkos.testshopapp.data.models.BestSellerApi
import com.molkos.testshopapp.data.models.HomeStoreApi
import com.molkos.testshopapp.data.models.ProductApi
import com.molkos.testshopapp.data.storage.Product
import com.molkos.testshopapp.domain.models.*

fun HomeStoreApi.toHotSalesProduct(): HotSalesProduct =
    HotSalesProduct(
        id = id,
        isNew = isNew,
        title = title,
        subtitle = subtitle,
        picture = picture,
        isBuy = isBuy
    )


fun BestSellerApi.toBestSellersProduct(): BestSellersProduct =
    BestSellersProduct(
        id = id,
        isFavorites = isFavorites,
        title = title,
        priceWithoutDiscount = priceWithoutDiscount,
        discountPrice = discountPrice,
        picture = picture
    )


fun ProductApi.toProductDetails(): ProductDetails =
    ProductDetails(
        id = id,
        title = title,
        images = images.toList().map { ProductDetailsImage(imageUrl = it) },
        cpu = cpu,
        camera = camera,
        capacity = capacity,
        colors = colors,
        isFavorite = isFavorite,
        price = price,
        rating = rating,
        sd = sd,
        ssd = ssd
    )

fun Product.toProductInCart(): ProductInCart =
    ProductInCart(
        title = title,
        pricePerOne = price,
        imageUrl = imageUrl,
        count = count
    )

fun ProductInCart.toProduct(): Product =
    Product(
        title = title,
        price = pricePerOne,
        imageUrl = imageUrl,
        count = count
    )
