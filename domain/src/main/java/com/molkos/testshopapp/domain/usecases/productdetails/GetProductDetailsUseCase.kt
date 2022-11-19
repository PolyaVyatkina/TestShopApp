package com.molkos.testshopapp.domain.usecases.productdetails

import com.molkos.testshopapp.domain.models.Response
import com.molkos.testshopapp.domain.repositories.ProductDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetProductDetailsUseCase(private val productDetailsRepository: ProductDetailsRepository) {

    fun execute(): Flow<Response> {
        return productDetailsRepository.productDetails
    }
}