package com.molkos.testshopapp.domain.repositories

import com.molkos.testshopapp.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface ProductDetailsRepository {

    val productDetails: Flow<Response>
}