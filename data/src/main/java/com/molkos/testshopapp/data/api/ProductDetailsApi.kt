package com.molkos.testshopapp.data.api

import com.molkos.testshopapp.data.models.ProductApi
import com.molkos.testshopapp.domain.models.Response
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*

class ProductDetailsApi(private val client: HttpClient) {

    suspend fun fetchProductDetails(): Response {
        return try {
            val res: ProductApi = client.request("https://run.mocky.io/v3/6c14c560-15c6-4248-b9d2-b4508df7d4f5")
            Response.Success(res)
        } catch (ex: ResponseException) {
            Response.Error("Bad response:$ex.response. Text:\"$ex.cachedResponseText\"")
        } catch (t: Throwable) {
            Response.Error(t.message.toString())
        }
    }
}