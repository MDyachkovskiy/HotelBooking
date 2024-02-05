package com.test.application.booking

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.test.application.R
import com.test.application.core.domain.Booking
import com.test.application.core.navigation.Navigator
import com.test.application.core.utilities.AppState
import com.test.application.core.utilities.formatExactPrice
import com.test.application.core.utilities.isValidEmail
import com.test.application.core.utilities.setupDoneActionForEditText
import com.test.application.core.view.BaseFragment
import com.test.application.databinding.FragmentBookingBinding
import com.test.application.databinding.TouristInfoBlockBinding
import com.test.application.features.AnimationHelper
import com.test.application.features.FieldsValidator
import com.test.application.features.TouristInfoManager
import com.test.application.utils.calculateTotalTourPrice
import com.test.application.utils.formatTourDate
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingFragment : BaseFragment<AppState, Booking, FragmentBookingBinding>(
    FragmentBookingBinding::inflate
) {

    private val model: BookingViewModel by viewModel()
    private lateinit var touristInfoManager: TouristInfoManager
    private lateinit var fieldsValidator: FieldsValidator
    private lateinit var resources: Resources

    override fun findProgressBar(): FrameLayout {
        return binding.progressBar
    }

    override fun setupData(data: Booking) {
        initTextInformation(data)
        initButtons(data)
    }

    private fun initButtons(data: Booking) {
        val totalPrice = formatExactPrice(calculateTotalTourPrice(data), resources)
        binding.payButton.text = getString(R.string.pay_button_text, totalPrice)
    }

    private fun initTextInformation(data: Booking) {
        setHotelInformatiion(data)
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
            tvTourPrice.text = formatExactPrice(data.tourPrice, resources)
            tvFuelCharge.text = formatExactPrice(data.fuelCharge, resources)
            tvServiceCharge.text = formatExactPrice(data.serviceCharge, resources)
            tvTotalPrice.text = formatExactPrice(calculateTotalTourPrice(data), resources)
        }
    }

    private fun setHotelInformatiion(data: Booking) {
        with(binding) {
            tvRating.text = data.hotelRating.toString()
            tvRatingName.text = data.ratingName
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
        binding.touristAddButton.setOnClickListener {
            touristInfoManager =
                TouristInfoManager(requireContext(), binding.dynamicContainerForNewTourist)
            touristInfoManager.addNewTouristBlock()
        }
        binding.payButton.setOnClickListener {
            validateEditFields()
        }
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
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
                val isCurrentlyVisible = etName.visibility == View.VISIBLE
                AnimationHelper.animateVisibility(
                    binding.dynamicContainerForNewTourist,
                    listOf(etName, etSecondName, etCitizenship, etBirthDate, etPassportNumber, etPassportExpiringDate),
                    !isCurrentlyVisible
                )
                AnimationHelper.animateArrowRotation(openButtonArrow, !isCurrentlyVisible)
            }
        }
    }

    private fun validateEditFields() {
        fieldsValidator = FieldsValidator(requireContext())
        val touristInfoView = binding.touristInformationBlock

        val textInputLayouts = listOf(
            touristInfoView.findViewById(R.id.et_name),
            touristInfoView.findViewById(R.id.et_second_name),
            touristInfoView.findViewById(R.id.et_citizenship),
            touristInfoView.findViewById(R.id.et_birth_date),
            touristInfoView.findViewById(R.id.et_passport_number),
            touristInfoView.findViewById<TextInputLayout>(R.id.et_passport_expiring_date)
        )
        val allFieldsValid = fieldsValidator.validateFields(textInputLayouts)

        if(allFieldsValid) {
            (activity as? Navigator)?.navigateFromBookingToPaymentSuccess()
        } else {
            fieldsValidator.showErrorToast(getString(R.string.fill_all_necessary_edits))
        }
    }

    private fun setupEmailEditText() {
        val emailEditText = binding.editTextEmail
        val emailLayout = binding.etEmail
        setupDoneActionForEditText(emailEditText)

        emailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (!isValidEmail(emailEditText.text.toString())) {
                    setFieldErrorColor(emailLayout)
                    showErrorToast(getString(R.string.incorrect_email))
                } else {
                    resetEditTextFieldToDefaultColor(emailLayout)
                }
            }
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun setFieldErrorColor(textInputLayout: TextInputLayout) {
        val alertColor = ContextCompat
            .getColor(
                requireContext(),
                com.test.application.core.R.color.edit_text_alert
            )
        val backgroundColor = ColorUtils
            .setAlphaComponent(alertColor, 38)
        textInputLayout.boxBackgroundColor = backgroundColor
    }

    private fun resetEditTextFieldToDefaultColor(textInputLayout: TextInputLayout) {
        textInputLayout.boxBackgroundColor = ContextCompat
            .getColor(
                requireContext(),
                com.test.application.core.R.color.edit_text_background
            )
    }

    private fun setupPhoneEditText() {
        val editTextPhone = binding.editTextPhoneNumber
        setupDoneActionForEditText(editTextPhone)
        val listener = MaskedTextChangedListener(
            format = getString(R.string.phone_format),
            field = editTextPhone
        )
        editTextPhone.addTextChangedListener(listener)
        editTextPhone.onFocusChangeListener = listener
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
}