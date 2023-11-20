package com.test.application.room_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.test.application.room_list.databinding.ItemRoomImageBinding

class RoomImageAdapter(
    private val images: List<String>
) : RecyclerView.Adapter<RoomImageAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(
        private val binding: ItemRoomImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            binding.imageView.load(imageUrl) {
                crossfade(true)
                placeholder(com.test.application.core.R.drawable.progress_bar)
                error(com.test.application.core.R.drawable.default_placeholder)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val binding = ItemRoomImageBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return SliderViewHolder(binding)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(images[position])
    }
}