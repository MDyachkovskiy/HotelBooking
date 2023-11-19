package com.test.application.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.google.android.material.chip.Chip
import com.test.application.core.domain.AboutTheHotel
import com.test.application.core.domain.Hotel
import com.test.application.core.utilities.AppState
import com.test.application.core.view.BaseFragment
import com.test.application.home.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<AppState, Hotel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val model: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.stateFlow.collect {appState ->
                    renderData(appState)
                }
            }
        }
        requestData()
    }

    private fun requestData() {
        model.getData()
    }

    override fun setupData(data: Hotel) {
        initTextInformation(data)
        initImages(data.imageUrls)
        initPeculiaritiesChips(data.aboutTheHotel)
    }

    private fun initTextInformation(data: Hotel) {
        with(binding) {
            tvHotelName.text = data.name
            tvHotelAddress.text = data.address
            tvPrice.text = formatPrice(data.minimalPrice)
            tvPriceDescription.text = data.priceForIt
            tvRating.text = data.rating.toString()
            tvHotelName.text  = data.ratingName
            tvHotelDescription.text = data.aboutTheHotel?.description
        }
    }

    private fun formatPrice(price: Int?): String {
        return "от $price ₽"
    }

    private fun initPeculiaritiesChips(aboutTheHotel: AboutTheHotel?) {
        aboutTheHotel?.peculiarities?.let {peculiarities ->
            val chipGroup = binding.hotelPeculiaritiesChips
            chipGroup.removeAllViews()

            peculiarities.forEach {peculiarity ->
                val chip = Chip(context, null,
                    com.test.application.core.R.style.hotel_peculiarities_style).apply {
                    text = peculiarity
                    isCheckable = false
                }
                chipGroup.addView(chip)
            }
        }
    }

    private fun initImages(imageUrls: List<String>?) {
        binding.hotelImages.load(imageUrls?.first()) {
            crossfade(true)
        }
    }

    override fun showErrorDialog(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}