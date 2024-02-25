package com.test.application.room_list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.application.common.utils.ChipInflaterManager
import com.test.application.common.utils.formatPrice
import com.test.application.core.domain.Room
import com.test.application.room_list.R
import com.test.application.room_list.databinding.ItemRoomBinding

class RoomListAdapter(
    private var context: Context?
) : RecyclerView.Adapter<RoomListAdapter.RoomViewHolder>() {

    private var rooms: List<Room> = listOf()
    var listener: (() -> Unit)? = null
    lateinit var chipInflaterManager: ChipInflaterManager

    fun updateData(newRooms: List<Room>) {
        val diffcallback = Diffcallback(rooms, newRooms)
        val diffResult =DiffUtil.calculateDiff(diffcallback)

        rooms = newRooms
        diffResult.dispatchUpdatesTo(this)
    }
    inner class RoomViewHolder(
        private val binding: ItemRoomBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(room: Room) {
            initTextInformation(room)
            initImages(room.imageUrls)
            initPeculiaritiesChips(room.peculiarities)

            binding.bookingButton.setOnClickListener {
                listener?.invoke()
            }
        }

        private fun initPeculiaritiesChips(peculiarities: List<String>?) {
            val chipGroup = binding.roomPeculiarities
            chipInflaterManager = ChipInflaterManager(context)

            val chipLayoutResId = R.layout.item_chip
            peculiarities?.let {
                chipInflaterManager.addPeculiaritiesChips(
                    it,
                    chipGroup,
                    chipLayoutResId
                )
            }
        }

        private fun initImages(imageUrls: List<String>?) {
            val viewPager = binding.imageCarousel
            val indicator = binding.imageIndicator

            val adapter = RoomImageAdapter(imageUrls ?: emptyList())
            viewPager.adapter = adapter
            indicator.setViewPager(viewPager)
        }

        private fun initTextInformation(room: Room) {
            with(binding) {
                tvRoomName.text = room.name
                tvPrice.text =
                    context?.let { formatPrice(room.price, it.resources) }
                tvPriceDescription.text = room.pricePer
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = ItemRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return RoomViewHolder(binding)
    }

    override fun getItemCount() = rooms.size

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(rooms[position])
    }

    fun cleanup() {
        context = null
    }
}