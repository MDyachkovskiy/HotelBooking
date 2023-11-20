package com.test.application.home

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ProgressBar
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.shape.CornerFamily
import com.test.application.core.domain.AboutTheHotel
import com.test.application.core.domain.Hotel
import com.test.application.core.navigation.Navigator
import com.test.application.core.utilities.AppState
import com.test.application.core.view.BaseFragment
import com.test.application.home.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.Locale

class HomeFragment : BaseFragment<AppState, Hotel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val model: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        initButtons()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initButtons() {
        binding.bookingButton.setOnClickListener {
            (activity as? Navigator)?.navigateToRoomListFragment()
        }
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
        model.loadHotelInfo()
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
        return if(price != null) {
            val formatter = NumberFormat.getNumberInstance(Locale("ru", "RU"))
            "от ${formatter.format(price)} ₽"
        } else {
            requireContext().getString(R.string.unknown_price)
        }
    }

    private fun initPeculiaritiesChips(aboutTheHotel: AboutTheHotel?) {
        aboutTheHotel?.peculiarities?.let {peculiarities ->
            val chipGroup = binding.hotelPeculiaritiesChips
            chipGroup.removeAllViews()

            peculiarities.forEach {peculiarity ->
                val chip = Chip(requireContext()).apply {
                    text = peculiarity
                    isCheckable = false

                    setChipBackgroundColorResource(R.color.peculiarities_chip_background)
                    val shapeAppearanceModel = shapeAppearanceModel.toBuilder()
                        .setAllCorners(CornerFamily.ROUNDED, resources.getDimension(R.dimen.chip_corner_radius))
                        .build()
                    this.shapeAppearanceModel = shapeAppearanceModel
                    chipStrokeWidth = 0f
                    setPadding(
                        resources.getDimensionPixelSize(R.dimen.margin_10dp_small),
                        resources.getDimensionPixelSize(R.dimen.margin_5dp_small),
                        resources.getDimensionPixelSize(R.dimen.margin_10dp_small),
                        resources.getDimensionPixelSize(R.dimen.margin_5dp_small)
                    )
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    setTextColor(resources.getColor(R.color.peculiarities_text_color, null))
                    typeface = ResourcesCompat.getFont(requireContext(), R.font.sf_pro_display_500)
                    lineHeight = resources.getDimensionPixelSize(R.dimen.chip_line_height)
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

    override fun findProgressBar(): ProgressBar {
        return binding.progressBar
    }
}