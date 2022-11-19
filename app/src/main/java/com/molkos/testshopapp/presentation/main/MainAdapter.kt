package com.molkos.testshopapp.presentation.main

import android.os.Parcelable
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.molkos.testshopapp.models.MainScreenParts
import com.molkos.testshopapp.models.displayableitem.BestSeller
import com.molkos.testshopapp.models.displayableitem.Category
import com.molkos.testshopapp.models.displayableitem.DisplayableItem
import com.molkos.testshopapp.presentation.adapters.delegates.*


class MainAdapter(
    glide: RequestManager,
    scrollStates: MutableMap<Int, Parcelable>,
    onCategoryClick: (category: Category) -> Unit,
    onBestSellerClick: (bestSeller: BestSeller) -> Unit,
    onFavIconClick: (bestSeller: BestSeller) -> Unit,
    onFilterClick: () -> Unit,
) : ListDelegationAdapter<List<DisplayableItem>>(
    searchDelegate(),
    locationAndFilterDelegate(onFilterClick = onFilterClick),
    categoriesDelegate(onItemClick = onCategoryClick, scrollStates = scrollStates),
    hotSalesDelegate(glide = glide, snapHelper = snapHelper),
    bestSellersDelegate(glide = glide, onItemClick = onBestSellerClick, onFavClick = onFavIconClick),
) {
    var parts: MainScreenParts = MainScreenParts()

    sealed class Payload {
        data class BestSellerFavChanged(val bestSeller: BestSeller) : Payload()
        object SelectedCategoryChanged : Payload()
    }
}

private val snapHelper: LinearSnapHelper = object : LinearSnapHelper() {
    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int,
    ): Int {
        val centerView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        val position = layoutManager.getPosition(centerView)
        var targetPosition = -1
        if (layoutManager.canScrollHorizontally()) {
            targetPosition = if (velocityX < 0) {
                position - 1
            } else {
                position + 1
            }
        }
        if (layoutManager.canScrollVertically()) {
            targetPosition = if (velocityY < 0) {
                position - 1
            } else {
                position + 1
            }
        }
        val firstItem = 0
        val lastItem = layoutManager.itemCount - 1
        targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem))
        return targetPosition
    }
}