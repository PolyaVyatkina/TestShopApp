package com.molkos.testshopapp.presentation.adapters.delegates

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.molkos.testshopapp.R
import com.molkos.testshopapp.databinding.ItemBestSellersBinding
import com.molkos.testshopapp.databinding.SectionBestSellersBinding
import com.molkos.testshopapp.models.displayableitem.BestSeller
import com.molkos.testshopapp.models.displayableitem.BestSellersList
import com.molkos.testshopapp.models.displayableitem.DisplayableItem
import com.molkos.testshopapp.presentation.adapters.delegates.BestSellersAdapter.BestSellerViewHolder
import com.molkos.testshopapp.presentation.main.MainAdapter


fun bestSellersDelegate(
    glide: RequestManager,
    onItemClick: (item: BestSeller) -> Unit,
    onFavClick: (item: BestSeller) -> Unit,
) =
    adapterDelegateViewBinding<BestSellersList, DisplayableItem, SectionBestSellersBinding>(
        { layoutInflater, container ->
            SectionBestSellersBinding.inflate(layoutInflater, container, false).apply {
                rv.adapter = BestSellersAdapter(glide, onItemClick, onFavClick)
            }
        }
    ) {
        bind { diffPayloads ->
            val bestSellersAdapter = binding.rv.adapter as BestSellersAdapter
            if (diffPayloads.isNotEmpty()) {
                for (payload in diffPayloads) {
                    if (payload is MainAdapter.Payload.BestSellerFavChanged) {
                        val changedItem = payload.bestSeller
                        val itemPosition = bestSellersAdapter.getItemPosition(changedItem)
                        bestSellersAdapter.notifyItemChanged(itemPosition, BestSellersAdapter.Payload.FAV_CHANGED)
                    }
                }
            } else bestSellersAdapter.apply {
                setNewItems(item.list)
            }
        }
    }

class BestSellersAdapter(
    private val glide: RequestManager,
    private val onItemClick: (item: BestSeller) -> Unit,
    private val onFavClick: (item: BestSeller) -> Unit,
) : RecyclerView.Adapter<BestSellerViewHolder>() {

    enum class Payload {
        FAV_CHANGED
    }

    private var items: List<BestSeller> = listOf()

    fun setNewItems(newItems: List<BestSeller>) {
        items = newItems
        notifyItemRangeChanged(0, itemCount)
    }

    fun getItemPosition(item: BestSeller): Int = items.indexOf(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewHolder {
        val binding = ItemBestSellersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BestSellerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty())
            for (payload in payloads) {
                if (payload == Payload.FAV_CHANGED)
                    holder.bindFavorite(items[position])
            }
        else super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int = items.size

    inner class BestSellerViewHolder(binding: ItemBestSellersBinding) : ViewHolder(binding.root) {

        private val image: ImageView
        private val favorite: ImageView
        private val discountPrice: TextView
        private val price: TextView
        private val title: TextView
        private val root: View

        init {
            image = binding.itemImg
            favorite = binding.favIcon
            discountPrice = binding.price
            price = binding.discountPrice
            title = binding.name
            root = binding.root
        }

        fun bind(item: BestSeller) {
            discountPrice.text = item.discountPrice.toString()
            discountPrice.paintFlags = discountPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            price.text = item.fullPrice.toString()
            title.text = item.title

            val iconFav = if (item.isFav) R.drawable.fav_on else R.drawable.fav_off
            favorite.setImageResource(iconFav)

            glide.load(item.imageURL)
                .placeholder(R.drawable.placeholder_phone)
                .into(image)

            favorite.setOnClickListener { onFavClick.invoke(item) }

            root.setOnClickListener { onItemClick.invoke(item) }
        }

        fun bindFavorite(item: BestSeller) {
            val isFav = !item.isFav
            val iconFav = if (isFav) R.drawable.fav_on else R.drawable.fav_off
            favorite.setImageResource(iconFav)
        }
    }
}