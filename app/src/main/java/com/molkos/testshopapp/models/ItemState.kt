package com.molkos.testshopapp.models

sealed class ItemState {
    data class Removed(val position: Int): ItemState()
    data class Changed(val position: Int): ItemState()
    object Default: ItemState()
}

