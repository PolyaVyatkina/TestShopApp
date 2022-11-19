package com.molkos.testshopapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeStoreAndBestSeller(
    @SerialName("home_store") val homeStore: Array<HomeStoreApi>,
    @SerialName("best_seller") val bestSeller: Array<BestSellerApi>,
) : ResponseBodyApi {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HomeStoreAndBestSeller

        if (!homeStore.contentEquals(other.homeStore)) return false
        if (!bestSeller.contentEquals(other.bestSeller)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = homeStore.contentHashCode()
        result = 31 * result + bestSeller.contentHashCode()
        return result
    }
}