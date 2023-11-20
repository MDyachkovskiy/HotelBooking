package com.test.application.room_list

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.shape.CornerFamily
import com.test.application.core.domain.Room
import com.test.application.room_list.databinding.ItemRoomBinding
import java.text.NumberFormat
import java.util.Locale

class RoomListAdapter(
    initialRooms: List<Room>,
    private val context: Context
) : RecyclerView.Adapter<RoomListAdapter.RoomViewHolder>() {

    private val rooms = initialRooms.toMutableList()

    fun updateData(newRooms: List<Room>) {
        rooms.clear()
        rooms.addAll(newRooms)
        notifyDataSetChanged()
    }
    inner class RoomViewHolder(
        private val binding: ItemRoomBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(room: Room) {
            initTextInformation(room)
            initImages(room.imageUrls)
            initPeculiaritiesChips(room.peculiarities)
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
                tvPrice.text = formatPrice(room.price)
                tvPriceDescription.text = room.pricePer
            }
        }

        private fun formatPrice(price: Int?): String {
            return if(price != null) {
                val formatter = NumberFormat.getNumberInstance(Locale("ru", "RU"))
                "от ${formatter.format(price)} ₽"
            } else {
                context.getString(R.string.unknown_price)
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