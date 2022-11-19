package com.molkos.testshopapp.presentation.adapters.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.molkos.testshopapp.R
import com.molkos.testshopapp.databinding.ItemHotSalesBinding
import com.molkos.testshopapp.databinding.SectionHotSalesBinding
import com.molkos.testshopapp.models.displayableitem.DisplayableItem
import com.molkos.testshopapp.models.displayableitem.HotSale
import com.molkos.testshopapp.models.displayableitem.HotSalesList


fun hotSalesDelegate(
    glide: RequestManager,
    snapHelper: LinearSnapHelper,
) =
    adapterDelegateViewBinding<HotSalesList, DisplayableItem, SectionHotSalesBinding>(
        { layoutInflater, container ->
            SectionHotSalesBinding.inflate(layoutInflater, container, false).apply {
                rv.adapter = HotSalesAdapter(glide)
            }
        }
    ) {
        bind { diffPayloads ->
            (binding.rv.adapter as HotSalesAdapter).apply {
                setNewItems(item.list)
            }
            snapHelper.attachToRecyclerView(binding.rv)
        }

    }

class HotSalesAdapter(
    private val glide: RequestManager,
) : RecyclerView.Adapter<HotSalesAdapter.HotSaleViewHolder>() {

    private var items: List<HotSale> = listOf()

    fun setNewItems(newItems: List<HotSale>) {
        items = newItems
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSaleViewHolder {
        val binding = ItemHotSalesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotSaleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotSaleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


    inner class HotSaleViewHolder(binding: ItemHotSalesBinding) : RecyclerView.ViewHolder(binding.root) {

        private val new: TextView
        private val title: TextView
        private val description: TextView
        private val buttonBuy: TextView
        private val image: ImageView

        init {
            new = binding.newIcon
            title = binding.name
            description = binding.description
            buttonBuy = binding.buyBtn
            image = binding.itemImg
        }

        fun bind(item: HotSale) {
            new.visibility = if (item.isNew) View.INVISIBLE else View.VISIBLE
            title.text = item.title
            description.text = item.description

            glide.load(item.imageURL)
                .placeholder(R.drawable.placeholder_hot_sales)
                .into(image)
        }

    }
}