package com.molkos.testshopapp.presentation.adapters.delegates

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.molkos.testshopapp.R
import com.molkos.testshopapp.databinding.ItemCategoryBinding
import com.molkos.testshopapp.databinding.SectionCategoryBinding
import com.molkos.testshopapp.models.displayableitem.Category
import com.molkos.testshopapp.models.displayableitem.CategoryList
import com.molkos.testshopapp.models.displayableitem.CategoryTitle
import com.molkos.testshopapp.models.displayableitem.DisplayableItem


fun categoriesDelegate(
    onItemClick: (item: Category) -> Unit,
    scrollStates: MutableMap<Int, Parcelable>,
) =
    adapterDelegateViewBinding<CategoryList, DisplayableItem, SectionCategoryBinding>(
        { layoutInflater, container ->
            SectionCategoryBinding.inflate(layoutInflater, container, false).apply {
                rv.adapter = CategoryAdapter(onItemClick)
            }
        }
    ) {
        bind { diffPayloads ->
            val categoryAdapter = binding.rv.adapter as CategoryAdapter
            categoryAdapter.apply {
                setNewItems(item.list)
            }

            (binding.rv.adapter as CategoryAdapter).stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.ALLOW

            scrollStates[absoluteAdapterPosition]?.let {
                binding.rv.layoutManager?.onRestoreInstanceState(it)
                scrollStates.remove(absoluteAdapterPosition)
            }

        }
        onViewRecycled {
            binding.rv.layoutManager?.onSaveInstanceState()?.let {
                scrollStates[absoluteAdapterPosition] = it
            }
        }

    }

class CategoryAdapter(
    private val onItemClick: (item: Category) -> Unit,
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var items: List<Category> = listOf()

    fun setNewItems(newItems: List<Category>) {
        items = newItems
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CategoryViewHolder(binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        private val title: TextView
        private val icon: ImageView
        private val root: View

        init {
            title = binding.title
            icon = binding.icon
            root = binding.root
        }

        fun bind(item: Category) {
            val context = root.context
            val drawable = when (item.title) {
                CategoryTitle.PHONES -> {
                    title.text = context.getString(R.string.category_phones)
                    if (item.isSelected) R.drawable.ellipse_phones_on
                    else R.drawable.ellipse_phones_off
                }
                CategoryTitle.COMPUTER -> {
                    title.text = context.getString(R.string.category_computer)
                    if (item.isSelected) R.drawable.ellipse_computer_on
                    else R.drawable.ellipse_computer_off
                }
                CategoryTitle.HEALTH -> {
                    title.text = context.getString(R.string.category_health)
                    if (item.isSelected) R.drawable.ellipse_health_on
                    else R.drawable.ellipse_health_off
                }
                CategoryTitle.BOOKS -> {
                    title.text = context.getString(R.string.category_books)
                    if (item.isSelected) R.drawable.ellipse_books_on
                    else R.drawable.ellipse_books_off
                }
                CategoryTitle.TOOLS -> {
                    title.text = context.getString(R.string.category_tools)
                    if (item.isSelected) R.drawable.ellipse_tools_on
                    else R.drawable.ellipse_tools_off
                }
            }
            icon.setImageResource(drawable)
            root.setOnClickListener { onItemClick.invoke(item) }
        }
    }

}