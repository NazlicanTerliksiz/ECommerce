package com.nazlican.ecommerce.ui.productDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.nazlican.ecommerce.databinding.ItemViewViewPagerBinding
import com.nazlican.ecommerce.util.extensions.downloadFromUrl

class ViewPagerAdapter(
    private val imageList: ArrayList<String>,
    private val viewPager: ViewPager2
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(private val binding: ItemViewViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            binding.viewPagerImageView.downloadFromUrl(imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding =
            ItemViewViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val image = imageList[position]
        holder.bind(image)
        if (position == imageList.size - 1) {
            viewPager.post(runnable)
        }
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }

}