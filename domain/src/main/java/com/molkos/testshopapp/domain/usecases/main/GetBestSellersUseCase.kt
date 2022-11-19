package com.molkos.testshopapp.domain.usecases.main

import com.molkos.testshopapp.domain.models.BestSellersProduct
import com.molkos.testshopapp.domain.repositories.BestSellersRepository
import kotlinx.coroutines.flow.Flow

typealias BestSellers = List<BestSellersProduct>

class GetBestSellersUseCase(private val bestSellersRepository: BestSellersRepository) {

    fun execute(): Flow<BestSellers> {
        return bestSellersRepository.bestSellers
    }
}