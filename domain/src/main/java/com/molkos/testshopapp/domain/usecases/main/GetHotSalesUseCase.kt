package com.molkos.testshopapp.domain.usecases.main

import com.molkos.testshopapp.domain.models.HotSalesProduct
import com.molkos.testshopapp.domain.repositories.HotSalesRepository
import kotlinx.coroutines.flow.Flow

typealias HotSales = List<HotSalesProduct>

class GetHotSalesUseCase(private val hotSalesRepository: HotSalesRepository) {

    fun execute(): Flow<HotSales> {
        return hotSalesRepository.hotSales
    }
}