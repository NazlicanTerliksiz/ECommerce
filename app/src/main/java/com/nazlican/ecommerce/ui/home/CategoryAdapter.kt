package com.nazlican.ecommerce.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazlican.ecommerce.databinding.ItemViewCategoriesBinding

class CategoryAdapter (

    private val onItemClickListener: (String) -> Unit
) :
RecyclerView.Adapter<CategoryAdapter.RowHolder>() {

    private val categoryList = ArrayList<String>()

    inner class RowHolder(private val binding: ItemViewCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            binding.apply {
                categoryName.text = category
                categoryName.setOnClickListener {
                    onItemClickListener.invoke(category)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding =
            ItemViewCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val category = categoryList[position]
        holder.bind(category)

    }
    fun updateList(updateList:List<String>){
        categoryList.clear()
        categoryList.addAll(updateList)
        notifyDataSetChanged()
    }
}