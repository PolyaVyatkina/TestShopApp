package com.molkos.testshopapp.domain.models

data class HotSalesProduct(
    val id: Int,
    val isNew: Boolean? = null,
    val title: String,
    val subtitle: String,
    val picture: String,
    val isBuy: Boolean,
) : ResponseBody
