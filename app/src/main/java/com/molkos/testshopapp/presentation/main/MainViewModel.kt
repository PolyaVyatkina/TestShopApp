package com.molkos.testshopapp.presentation.main

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molkos.testshopapp.domain.models.FilterOptions
import com.molkos.testshopapp.domain.usecases.main.GetAvailableFilterOptionsUseCase
import com.molkos.testshopapp.domain.usecases.main.GetBestSellersUseCase
import com.molkos.testshopapp.domain.usecases.main.GetHotSalesUseCase
import com.molkos.testshopapp.mappers.toDisplayableItem
import com.molkos.testshopapp.models.UiState
import com.molkos.testshopapp.models.displayableitem.Category
import com.molkos.testshopapp.models.displayableitem.CategoryList
import com.molkos.testshopapp.models.displayableitem.defaultCategoryList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainViewModel(
    private val getHotSalesUseCase: GetHotSalesUseCase,
    private val getBestSellersUseCase: GetBestSellersUseCase,
    private val getAvailableFilterOptionsUseCase: GetAvailableFilterOptionsUseCase,
) : ViewModel() {

    private val _hotSales: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val hotSales: StateFlow<UiState> = _hotSales

    private val _bestSellers: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val bestSellers: StateFlow<UiState> = _bestSellers

    private val _availableFilters: MutableStateFlow<FilterOptions> = MutableStateFlow(FilterOptions())
    val availableFilters: StateFlow<FilterOptions> = _availableFilters

    private val _categories: MutableStateFlow<CategoryList> = MutableStateFlow(defaultCategoryList)
    val categories: StateFlow<CategoryList> = _categories

    val onCategoryClick: (category: Category) -> Unit = { item ->
        val newCategories = categories.value.list.map {
            if (it.title == item.title)
                Category(title = it.title, isSelected = true)
            else
                Category(title = it.title, isSelected = false)
        }
        _categories.value = CategoryList(list = newCategories)
    }

    val scrollStates = mutableMapOf<Int, Parcelable>()

    init {
        viewModelScope.launch {
            getHotSalesUseCase.execute().collect { list ->
                val hotSalesDisplayable = list.map { it.toDisplayableItem() }
                _hotSales.value = UiState.Success(hotSalesDisplayable)
            }
            getBestSellersUseCase.execute().collect { list ->
                val bestSellersDisplayable = list.map { it.toDisplayableItem() }
                _bestSellers.value = UiState.Success(bestSellersDisplayable)
                _availableFilters.value = getAvailableFilterOptionsUseCase.execute(list)
            }
        }
    }

}