package com.molkos.testshopapp.di

import com.molkos.testshopapp.data.api.HotSalesAndBestSellersApi
import com.molkos.testshopapp.data.api.KtorClient
import com.molkos.testshopapp.data.api.ProductDetailsApi
import com.molkos.testshopapp.data.repositories.CartRepositoryImpl
import com.molkos.testshopapp.data.repositories.HotSalesAndBestSellersRepositoryImpl
import com.molkos.testshopapp.data.repositories.ProductDetailsRepositoryImpl
import com.molkos.testshopapp.data.storage.ProductStorage
import com.molkos.testshopapp.data.storage.SharedPrefsProductStorage
import com.molkos.testshopapp.domain.repositories.BestSellersRepository
import com.molkos.testshopapp.domain.repositories.CartRepository
import com.molkos.testshopapp.domain.repositories.HotSalesRepository
import com.molkos.testshopapp.domain.repositories.ProductDetailsRepository
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module


val dataModule = module {

    single {
        KtorClient.client
    }

    single {
        HotSalesAndBestSellersApi(client = get())
    }

    single {
        ProductDetailsApi(client = get())
    }

    single {
        HotSalesAndBestSellersRepositoryImpl(api = get())
    } binds arrayOf(HotSalesRepository::class, BestSellersRepository::class)

    single {
        ProductDetailsRepositoryImpl(api = get())
    } bind ProductDetailsRepository::class

    single {
        CartRepositoryImpl(storage = get())
    } bind CartRepository::class

    single {
        SharedPrefsProductStorage(context = get())
    } bind ProductStorage::class
}