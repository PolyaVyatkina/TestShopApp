package com.molkos.testshopapp.domain.repositories

import com.molkos.testshopapp.domain.models.BestSellersProduct
import kotlinx.coroutines.flow.Flow

interface BestSellersRepository {

    val bestSellers: Flow<List<BestSellersProduct>>
}