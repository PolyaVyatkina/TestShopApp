package com.molkos.testshopapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.molkos.testshopapp.R
import com.molkos.testshopapp.databinding.ItemCartBinding
import com.molkos.testshopapp.domain.models.ProductInCart

class CartAdapter(
    private val glide: RequestManager,
    private val onDeleteClick: (item: ProductInCart) -> Unit,
    private val onPlusClick: (item: ProductInCart) -> Unit,
    private val onMinusClick: (item: ProductInCart) -> Unit,
) : RecyclerView.Adapter<CartAdapter.ItemCartViewHolder>() {

    private var items: List<ProductInCart> = listOf()

    fun setItems(items: List<ProductInCart>) {
        this.items = items
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemCartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemCartViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty())
            holder.bindCount(items[position])
        super.onBindViewHolder(holder, position, payloads)
    }

    enum class Payload {
        COUNT_CHANGED
    }

    inner class ItemCartViewHolder(binding: ItemCartBinding) : ViewHolder(binding.root) {

        private val image: ImageView
        private val title: TextView
        private val price: TextView
        private val bin: ImageView
        private val minus: ImageView
        private val plus: ImageView
        private val count: TextView

        init {
            image = binding.image
            title = binding.title
            price = binding.commonPrice
            bin = binding.bin
            minus = binding.minus
            plus = binding.plus
            count = binding.count
        }

        fun bind(item: ProductInCart) {
            val context = image.context
            title.text = item.title
            price.text = context.getString(R.string.cart_item_price).format(item.pricePerOne)
            count.text = item.count.toString()

            plus.setOnClickListener {
                onPlusClick.invoke(item)
            }
            minus.setOnClickListener {
                onMinusClick.invoke(item)
            }
            bin.setOnClickListener {
                onDeleteClick.invoke(item)
            }

            glide.load(item.imageUrl)
                .placeholder(R.drawable.placeholder_product_in_cart)
                .into(image)
        }

        fun bindCount(item: ProductInCart) {
            count.text = item.count.toString()
        }

    }
}