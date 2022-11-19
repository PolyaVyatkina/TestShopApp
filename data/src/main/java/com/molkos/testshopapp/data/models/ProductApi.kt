package com.molkos.testshopapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductApi(
    @SerialName("CPU") val cpu: String,
    @SerialName("camera") val camera: String,
    @SerialName("capacity") val capacity: Array<String>,
    @SerialName("color") val colors: Array<String>,
    @SerialName("id") val id: Int,
    @SerialName("images") val images: Array<String>,
    @SerialName("isFavorites") val isFavorite: Boolean,
    @SerialName("price") val price: Int,
    @SerialName("rating") val rating: Double,
    @SerialName("sd") val sd: String,
    @SerialName("ssd") val ssd: String,
    @SerialName("title") val title: String,
) : ResponseBodyApi {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductApi

        if (cpu != other.cpu) return false
        if (camera != other.camera) return false
        if (!capacity.contentEquals(other.capacity)) return false
        if (!colors.contentEquals(other.colors)) return false
        if (id != other.id) return false
        if (!images.contentEquals(other.images)) return false
        if (isFavorite != other.isFavorite) return false
        if (price != other.price) return false
        if (rating != other.rating) return false
        if (sd != other.sd) return false
        if (ssd != other.ssd) return false
        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cpu.hashCode()
        result = 31 * result + camera.hashCode()
        result = 31 * result + capacity.contentHashCode()
        result = 31 * result + colors.contentHashCode()
        result = 31 * result + id
        result = 31 * result + images.contentHashCode()
        result = 31 * result + isFavorite.hashCode()
        result = 31 * result + price
        result = 31 * result + rating.hashCode()
        result = 31 * result + sd.hashCode()
        result = 31 * result + ssd.hashCode()
        result = 31 * result + title.hashCode()
        return result
    }
}