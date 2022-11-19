package com.molkos.testshopapp.di

import com.molkos.testshopapp.presentation.CommonViewModel
import com.molkos.testshopapp.presentation.cart.CartViewModel
import com.molkos.testshopapp.presentation.details.ProductDetailsViewModel
import com.molkos.testshopapp.presentation.main.MainViewModel
import org.koin.dsl.module

val appModule = module {

    single {
        CommonViewModel(
            getAmountOfItemsInCart = get()
        )
    }

    single {
        MainViewModel(
            getHotSalesUseCase = get(),
            getBestSellersUseCase = get(),
            getAvailableFilterOptionsUseCase = get()
        )
    }

    single {
        ProductDetailsViewModel(
            getProductDetailsUseCase = get(),
            addProductToCartUseCase = get()
        )
    }

    single {
        CartViewModel(
            getProductsInCartUseCase = get(),
            removeProductFromCartUseCase = get(),
            changeProductCountUseCase = get()
        )
    }
}