package com.test.application.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.test.application.home.databinding.ItemHotelImageBinding

class HotelImageAdapter(
    private val images: List<String>
) : RecyclerView.Adapter<HotelImageAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(
        private val binding:ItemHotelImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            binding.imageView.load(imageUrl) {
                crossfade(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val binding = ItemHotelImageBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return SliderViewHolder(binding)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(images[position])
    }
}