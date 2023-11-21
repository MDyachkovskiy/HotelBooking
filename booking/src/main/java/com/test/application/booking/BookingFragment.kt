package com.test.application.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.test.application.R
import com.test.application.core.domain.Booking
import com.test.application.core.navigation.Navigator
import com.test.application.core.utilities.AppState
import com.test.application.core.utilities.formatExactPrice
import com.test.application.core.utilities.getOrdinalTourist
import com.test.application.core.view.BaseFragment
import com.test.application.databinding.FragmentBookingBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingFragment : BaseFragment<AppState, Booking, FragmentBookingBinding>(
    FragmentBookingBinding::inflate
) {

    private val model: BookingViewModel by viewModel()

    private var lastAddedView: View? = null
    private var touristCount = 1
    override fun findProgressBar(): FrameLayout {
        return binding.progressBar
    }

    override fun setupData(data: Booking) {
        initTextInformation(data)
        initButtons(data)
    }

    private fun initButtons(data: Booking) {
        val totalPrice = formatExactPrice(calculateTotalTourPrice(data))
        binding.payButton.text = getString(R.string.pay_button_text, totalPrice)
    }

    private fun initTextInformation(data: Booking) {
        with(binding) {
            tvRating.text = data.hotelRating.toString()
            tvRatingName.text = data.ratingName
            tvHotelName.text = data.hotelName
            tvHotelAddress.text = data.hotelAddress

            tvDeparture.text = data.departure
            tvArrivalCountry.text = data.arrivalCountry
            tvTourDate.text = formatTourDate(data.tourDateStart, data.tourDateStop)
            tvNumberOfNights.text = data.numberOfNights.toString()
            tvHotel.text = data.hotelName
            tvRoom.text = data.room
            tvNutrition.text = data.nutrition

            tvTourPrice.text = formatExactPrice(data.tourPrice)
            tvFuelCharge.text = formatExactPrice(data.fuelCharge)
            tvServiceCharge.text = formatExactPrice(data.serviceCharge)
            tvTotalPrice.text = formatExactPrice(calculateTotalTourPrice(data))
        }
    }

    private fun formatTourDate(tourDateStart: String, tourDateStop: String): String {
        return "$tourDateStart – $tourDateStop"
    }

    private fun calculateTotalTourPrice(data: Booking): Int {
        return data.tourPrice + (data.fuelCharge ?: 0) + (data.serviceCharge ?: 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        initBackButton()
        initAddTouristButton()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initAddTouristButton() {
        binding.touristAddButton.setOnClickListener {
            addNewTouristBlock()
        }
    }

    private fun addNewTouristBlock() {
        val touristInfoBlock = createTouristInfoBlock()
        updateTouristTitle(touristInfoBlock, ++touristCount)
        setLayoutTopMargin(touristInfoBlock)
        addTouristBlockToContainer(touristInfoBlock)
        updateLayoutConstraints(touristInfoBlock)
        lastAddedView = touristInfoBlock
    }

    private fun updateLayoutConstraints(touristInfoBlock: View) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.dynamicContainerForNewTourist)
        constraintSet.connect(
            touristInfoBlock.id,
            ConstraintSet.TOP,
            lastAddedView?.id ?: ConstraintSet.PARENT_ID,
            if (lastAddedView == null) ConstraintSet.TOP else ConstraintSet.BOTTOM)
        constraintSet.applyTo(binding.dynamicContainerForNewTourist)
    }

    private fun addTouristBlockToContainer(touristInfoBlock: View) {
        binding.dynamicContainerForNewTourist.addView(touristInfoBlock)
    }

    private fun setLayoutTopMargin(touristInfoBlock: View) {
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        ).apply{
            topMargin = (8 * resources.displayMetrics.density).toInt()
        }
        touristInfoBlock.layoutParams = layoutParams
    }

    private fun updateTouristTitle(touristInfoBlock: View, touristCount: Int) {
        val titleTextView = touristInfoBlock
            .findViewById<TextView>(R.id.tourist_information_block_title)
        titleTextView.text = getOrdinalTourist(touristCount)
    }

    private fun createTouristInfoBlock(): View {
        return LayoutInflater.from(requireContext())
            .inflate(R.layout.tourist_info_block,
                binding.dynamicContainerForNewTourist,
                false)
            .apply {
                if(id == View.NO_ID) {
                    id = View.generateViewId()
                }
            }
    }

    private fun initBackButton() {
        binding.backButton.setOnClickListener {
            (activity as? Navigator)?.navigateFromBookingToRoomList()
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
        model.loadBookingInfo()
    }
}