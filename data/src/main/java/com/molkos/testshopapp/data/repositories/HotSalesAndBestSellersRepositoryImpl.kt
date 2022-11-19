package com.molkos.testshopapp.data.repositories

import com.molkos.testshopapp.data.api.HotSalesAndBestSellersApi
import com.molkos.testshopapp.data.mappers.toBestSellersProduct
import com.molkos.testshopapp.data.mappers.toHotSalesProduct
import com.molkos.testshopapp.data.models.HomeStoreAndBestSeller
import com.molkos.testshopapp.domain.models.BestSellersProduct
import com.molkos.testshopapp.domain.models.HotSalesProduct
import com.molkos.testshopapp.domain.models.Response
import com.molkos.testshopapp.domain.repositories.BestSellersRepository
import com.molkos.testshopapp.domain.repositories.HotSalesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class HotSalesAndBestSellersRepositoryImpl(private val api: HotSalesAndBestSellersApi) : HotSalesRepository,
    BestSellersRepository {

    private val getHotSalesAndBestSellers: Flow<Response> = flow {
        val result = api.fetchHotSalesAndBestSellers()
        emit(result)
    }

    override val hotSales: Flow<List<HotSalesProduct>> = getHotSalesAndBestSellers.map { response ->
        when (response) {
            is Response.Error -> emptyList()
            is Response.Success -> (response.body as HomeStoreAndBestSeller).homeStore.toList()
                .map { it.toHotSalesProduct() }
        }
    }

    override val bestSellers: Flow<List<BestSellersProduct>> = getHotSalesAndBestSellers.map { response ->
        when (response) {
            is Response.Error -> emptyList()
            is Response.Success -> (response.body as HomeStoreAndBestSeller).bestSeller.toList()
                .map { it.toBestSellersProduct() }
        }
    }
}