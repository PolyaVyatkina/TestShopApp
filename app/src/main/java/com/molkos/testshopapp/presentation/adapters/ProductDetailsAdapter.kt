package com.molkos.testshopapp.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.molkos.testshopapp.presentation.details.ShopFragment

class ProductDetailsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = PAGES_COUNT

    override fun createFragment(position: Int): Fragment {
        return ShopFragment.newInstance()
    }

    companion object {
        const val PAGES_COUNT = 3
        val pages = listOf("Shop", "Details", "Features")
    }
}