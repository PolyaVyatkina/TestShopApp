package com.molkos.testshopapp.domain.repositories

import com.molkos.testshopapp.domain.models.HotSalesProduct
import kotlinx.coroutines.flow.Flow

interface HotSalesRepository {

    val hotSales: Flow<List<HotSalesProduct>>
}