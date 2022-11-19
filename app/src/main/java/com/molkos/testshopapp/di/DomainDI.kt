package com.molkos.testshopapp.di

import com.molkos.testshopapp.domain.usecases.cart.ChangeProductCountUseCase
import com.molkos.testshopapp.domain.usecases.cart.GetProductsInCartUseCase
import com.molkos.testshopapp.domain.usecases.cart.RemoveProductFromCartUseCase
import com.molkos.testshopapp.domain.usecases.main.GetAmountOfItemsInCart
import com.molkos.testshopapp.domain.usecases.main.GetAvailableFilterOptionsUseCase
import com.molkos.testshopapp.domain.usecases.main.GetBestSellersUseCase
import com.molkos.testshopapp.domain.usecases.main.GetHotSalesUseCase
import com.molkos.testshopapp.domain.usecases.productdetails.AddProductToCartUseCase
import com.molkos.testshopapp.domain.usecases.productdetails.GetProductDetailsUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetHotSalesUseCase(hotSalesRepository = get())
    }

    factory {
        GetBestSellersUseCase(bestSellersRepository = get())
    }

    factory {
        GetAvailableFilterOptionsUseCase()
    }

    factory {
        GetProductDetailsUseCase(productDetailsRepository = get())
    }

    factory {
        AddProductToCartUseCase(cartRepository = get())
    }

    factory {
        GetProductsInCartUseCase(cartRepository = get())
    }

    factory {
        RemoveProductFromCartUseCase(cartRepository = get())
    }

    factory {
        ChangeProductCountUseCase(cartRepository = get())
    }

    factory {
        GetAmountOfItemsInCart(cartRepository = get())
    }
}