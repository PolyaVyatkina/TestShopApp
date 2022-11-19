package com.molkos.testshopapp.data.api

import com.molkos.testshopapp.data.models.HomeStoreAndBestSeller
import com.molkos.testshopapp.domain.models.Response
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*

class HotSalesAndBestSellersApi(private val client: HttpClient) {

    suspend fun fetchHotSalesAndBestSellers(): Response {
        return try {
            val res: HomeStoreAndBestSeller =
                client.request("https://run.mocky.io/v3/654bd15e-b121-49ba-a588-960956b15175")
            Response.Success(res)
        } catch (ex: ResponseException) {
            Response.Error("Bad response:$ex.response. Text:\"$ex.cachedResponseText\"")
        } catch (t: Throwable) {
            Response.Error(t.message.toString())
        }
    }
}
