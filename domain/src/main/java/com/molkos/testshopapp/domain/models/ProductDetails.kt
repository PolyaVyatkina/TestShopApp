package com.molkos.testshopapp.domain.models

data class ProductDetails(
    val id: Int,
    val title: String,
    val images: List<ProductDetailsImage>,
    val cpu: String,
    val camera: String,
    val capacity: Array<String>,
    val colors: Array<String>,
    val isFavorite: Boolean,
    val price: Int,
    val rating: Double,
    val sd: String,
    val ssd: String,
) : ResponseBody {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductDetails

        if (id != other.id) return false
        if (title != other.title) return false
        if (cpu != other.cpu) return false
        if (camera != other.camera) return false
        if (!capacity.contentEquals(other.capacity)) return false
        if (!colors.contentEquals(other.colors)) return false
        if (isFavorite != other.isFavorite) return false
        if (price != other.price) return false
        if (rating != other.rating) return false
        if (sd != other.sd) return false
        if (ssd != other.ssd) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + cpu.hashCode()
        result = 31 * result + camera.hashCode()
        result = 31 * result + capacity.contentHashCode()
        result = 31 * result + colors.contentHashCode()
        result = 31 * result + isFavorite.hashCode()
        result = 31 * result + price
        result = 31 * result + rating.hashCode()
        result = 31 * result + sd.hashCode()
        result = 31 * result + ssd.hashCode()
        return result
    }
}
