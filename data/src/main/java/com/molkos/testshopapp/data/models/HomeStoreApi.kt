package com.molkos.testshopapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeStoreApi(
    @SerialName("id") val id: Int,
    @SerialName("is_new") val isNew: Boolean? = null,
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("picture") val picture: String,
    @SerialName("is_buy") val isBuy: Boolean,
)
