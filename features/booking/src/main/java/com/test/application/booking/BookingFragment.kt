package com.test.application.booking

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.test.application.R
import com.test.application.core.domain.Booking
import com.test.application.core.navigation.Navigator
import com.test.application.core.utilities.AppState
import com.test.application.core.view.BaseFragment
import com.test.application.databinding.FragmentBookingBinding
import com.test.application.databinding.TouristInfoBlockBinding
import com.test.application.utils.animation.AnimationHelper
import com.test.application.utils.validation.FieldsValidator
import com.test.application.utils.view_inflating.TouristInfoManager
import com.test.application.common.calculateTotalTourPrice
import com.test.application.common.formatTourDate
import com.test.application.common.utils.formatExactPrice
import com.test.application.common.utils.setupDoneActionForEditText
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingFragment : BaseFragment<AppState, Booking, FragmentBookingBinding>(
    FragmentBookingBinding::inflate
) {

    private val model: BookingViewModel by viewModel()
    private lateinit var touristInfoManager: TouristInfoManager
    private val fieldsValidator: FieldsValidator by lazy { FieldsValidator(requireContext()) }
    private lateinit var resources: Resources

    override fun findProgressBar(): FrameLayout {
        return binding.progressBar
    }

    override fun setupData(data: Booking) {
        initTextInformation(data)
        initButtons(data)
    }

    private fun initButtons(data: Booking) {
        val totalPrice = formatExactPrice(
            calculateTotalTourPrice(data),
            resources
        )
        binding.payButton.text = getString(R.string.pay_button_text, totalPrice)
    }

    private fun initTextInformation(data: Booking) {
        setHotelInformation(data)
        setPriceInformation(data)
        setTourInformation(data)
    }

    private fun setTourInformation(data: Booking) {
        with(binding) {
            tvDeparture.text = data.departure
            tvArrivalCountry.text = data.arrivalCountry
            tvTourDate.text = formatTourDate(data.tourDateStart, data.tourDateStop)
            tvNumberOfNights.text = data.numberOfNights.toString()
            tvHotel.text = data.hotelName
            tvRoom.text = data.room
            tvNutrition.text = data.nutrition
        }
    }

    private fun setPriceInformation(data: Booking) {
        resources = requireContext().resources
        with(binding) {
            tvTourPrice.text =
                formatExactPrice(data.tourPrice, resources)
            tvFuelCharge.text =
                formatExactPrice(data.fuelCharge, resources)
            tvServiceCharge.text =
                formatExactPrice(data.serviceCharge, resources)
            tvTotalPrice.text = formatExactPrice(
                calculateTotalTourPrice(data),
                resources
            )
        }
    }

    private fun setHotelInformation(data: Booking) {
        with(binding) {
            ratingBlock.tvRating.text = data.hotelRating.toString()
            ratingBlock.tvRatingName.text = data.ratingName
            tvHotelName.text = data.hotelName
            tvHotelAddress.text = data.hotelAddress
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setupButtons()
        setupInitialTouristBlock()
        setupPhoneEditText()
        setupEmailEditText()
    }

    private fun setupButtons() {
        initTouristAddButton()
        binding.payButton.setOnClickListener {
            validateEditFields()
        }
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initTouristAddButton() {
        touristInfoManager =
            TouristInfoManager(requireContext(), binding.dynamicContainerForNewTourist)
        binding.touristAddButton.setOnClickListener {
            touristInfoManager.addNewTouristBlock()
        }
    }

    private fun setupInitialTouristBlock() {
        val firstTouristInfoBlock = binding.touristInformationBlock
        val touristBlockBinding = TouristInfoBlockBinding.bind(firstTouristInfoBlock)
        setupOpenCloseButtonListener(touristBlockBinding)
    }

    private fun setupOpenCloseButtonListener(touristBlockBinding: TouristInfoBlockBinding) {
        with(touristBlockBinding) {
            openButton.setOnClickListener {
                val isCurrentlyVisible = textBlock.visibility == View.VISIBLE
                AnimationHelper.animateVisibility(textBlock, !isCurrentlyVisible)
                AnimationHelper.animateArrowRotation(openButtonArrow, !isCurrentlyVisible)
            }
        }
    }

    private fun validateEditFields() {
        if (fieldsValidator.validateAll(
                binding.touristInformationBlock,
                binding.dynamicContainerForNewTourist,
                binding.etEmail,
                binding.etPhoneNumber)) {
            (activity as? Navigator)?.navigateFromBookingToPaymentSuccess()
        } else {
            showErrorToast(getString(R.string.fill_all_necessary_edits))
        }
    }

    private fun setupEmailEditText() {
        val emailEditText = binding.editTextEmail
        setupDoneActionForEditText(emailEditText)
        emailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                fieldsValidator.validateEmail(binding.etEmail)
            }
        }
    }

    private fun setupPhoneEditText() {
        val editTextPhone = binding.editTextPhoneNumber
        setupDoneActionForEditText(editTextPhone)
        val listener = MaskedTextChangedListener(
            format = getString(R.string.phone_format),
            field = editTextPhone
        )
        editTextPhone.addTextChangedListener(listener)
        editTextPhone.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                fieldsValidator.validatePhoneNumber(binding.etPhoneNumber)
            }
        }
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.stateFlow.collect { appState ->
                    renderData(appState)
                }
            }
        }
        requestData()
    }

    private fun requestData() {
        model.loadBookingInfo()
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        fieldsValidator.cleanup()
        touristInfoManager.cleanup()
        super.onDestroyView()
    }
}