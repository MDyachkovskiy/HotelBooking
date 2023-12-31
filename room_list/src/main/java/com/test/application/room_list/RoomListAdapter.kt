package com.test.application.room_list

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.shape.CornerFamily
import com.test.application.core.domain.Room
import com.test.application.core.utilities.formatPrice
import com.test.application.room_list.databinding.ItemRoomBinding

class RoomListAdapter(
    private val context: Context
) : RecyclerView.Adapter<RoomListAdapter.RoomViewHolder>() {

    private var rooms: List<Room> = listOf()
    var listener: (() -> Unit)? = null

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
            chipGroup.removeAllViews()

            peculiarities?.forEach { peculiarity ->
                val chip = Chip(context).apply {
                    text = peculiarity
                    isCheckable = false

                    setChipBackgroundColorResource(R.color.peculiarities_chip_background)
                    val shapeAppearanceModel = shapeAppearanceModel.toBuilder()
                        .setAllCorners(CornerFamily.ROUNDED,
                            resources.getDimension(R.dimen.chip_corner_radius))
                        .build()
                    this.shapeAppearanceModel = shapeAppearanceModel
                    chipStrokeWidth = 0f
                    chipStartPadding = resources.getDimension(R.dimen.margin_10dp_small)
                    chipEndPadding = resources.getDimension(R.dimen.margin_10dp_small)

                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    setTextColor(resources.getColor(R.color.peculiarities_text_color, null))
                    typeface = ResourcesCompat.getFont(context,
                        com.test.application.core.R.font.sf_pro_display_500)
                    lineHeight = resources.getDimensionPixelSize(R.dimen.chip_line_height)
                }
                chipGroup.addView(chip)
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
                tvPrice.text = formatPrice(room.price, context.resources)
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
}