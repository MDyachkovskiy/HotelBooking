package com.test.application.common.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ChipInflaterManager(
    private var context: Context?
) {
    fun addPeculiaritiesChips(
        peculiarities: List<String>,
        chipGroup: ChipGroup,
        @LayoutRes chipLayoutResId: Int
    ) {
        val inflater = LayoutInflater.from(context)
        chipGroup.removeAllViews()
        peculiarities.forEach { peculiarity ->
            val chip = inflater.inflate(chipLayoutResId, chipGroup, false) as Chip
            chip.text = peculiarity
            chipGroup.addView(chip)
        }
    }

    fun cleanup() {
        context = null
    }
}