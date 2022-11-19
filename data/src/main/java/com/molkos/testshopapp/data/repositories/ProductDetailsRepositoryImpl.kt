package com.molkos.testshopapp.data.repositories

import com.molkos.testshopapp.data.api.ProductDetailsApi
import com.molkos.testshopapp.data.mappers.toProductDetails
import com.molkos.testshopapp.data.models.ProductApi
import com.molkos.testshopapp.domain.models.Response
import com.molkos.testshopapp.domain.repositories.ProductDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductDetailsRepositoryImpl(private val api: ProductDetailsApi) : ProductDetailsRepository {

    override val productDetails: Flow<Response> = flow {
        val result = when (val productDetails = api.fetchProductDetails()) {
            is Response.Error -> Response.Error(productDetails.error)
            is Response.Success -> {
                val details = (productDetails.body as ProductApi).toProductDetails()
                Response.Success(details)
            }
        }
        emit(result)

    }
}