package com.nazlican.ecommerce.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.ItemViewCategoriesBinding

class CategoryAdapter (

    private val onItemClickListener: (String) -> Unit
) :
RecyclerView.Adapter<CategoryAdapter.RowHolder>() {

    private val categoryList = ArrayList<String>()
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class RowHolder(private val binding: ItemViewCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String, position: Int) {
            binding.apply {
                categoryName.text = category
                if (position == selectedPosition) {
                    categoryName.setTextColor(ContextCompat.getColor(itemView.context, R.color.darkcoffe))
                } else {
                    categoryName.setTextColor(ContextCompat.getColor(itemView.context, R.color.grey)) // veya istediğiniz bir renk
                }
                itemView.setOnClickListener {
                    onItemClickListener.invoke(category)
                    val previousSelectedPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(previousSelectedPosition)
                    notifyItemChanged(selectedPosition)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val category = categoryList[position]
        holder.bind(category, position)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding =
            ItemViewCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
    fun updateList(updateList:List<String>){
        categoryList.clear()
        categoryList.addAll(updateList)
        notifyDataSetChanged()
    }
}