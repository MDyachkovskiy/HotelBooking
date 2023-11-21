package com.test.application.booking

import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.textfield.TextInputLayout
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.test.application.R
import com.test.application.core.domain.Booking
import com.test.application.core.navigation.Navigator
import com.test.application.core.utilities.AppState
import com.test.application.core.utilities.formatExactPrice
import com.test.application.core.utilities.getOrdinalTourist
import com.test.application.core.utilities.isValidEmail
import com.test.application.core.utilities.setupDoneActionForEditText
import com.test.application.core.view.BaseFragment
import com.test.application.databinding.FragmentBookingBinding
import com.test.application.databinding.TouristInfoBlockBinding
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
        setupInitialTouristBlock()
        setupPhoneEditText()
        setupEmailEditText()
        setupPayButton()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupPayButton() {
        binding.payButton.setOnClickListener {
            validateEditFields()
        }
    }

    private fun validateEditFields() {
        val touristInfoView = binding.touristInformationBlock

        val textInputLayouts = listOf(
            touristInfoView.findViewById(R.id.et_name),
            touristInfoView.findViewById(R.id.et_second_name),
            touristInfoView.findViewById(R.id.et_citizenship),
            touristInfoView.findViewById(R.id.et_birth_date),
            touristInfoView.findViewById(R.id.et_passport_number),
            touristInfoView.findViewById<TextInputLayout>(R.id.et_passport_expiring_date)
        )
        var allFieldsValid = true
        textInputLayouts.forEach { layout ->
            if (layout.editText?.text.isNullOrEmpty()) {
                setFieldErrorColor(layout)
                allFieldsValid = false
            } else {
                resetEditTextFieldToDefaultColor(layout)
            }
        }

        if (allFieldsValid) {
            (activity as? Navigator)?.navigateFromBookingToPaymentSuccess()
        } else {
            showErrorToast(getString(R.string.fill_all_necessary_edits))
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
            format = "+7 ([000]) [000]-[00]-[00]",
            field = editTextPhone
        )
        editTextPhone.addTextChangedListener(listener)
        editTextPhone.onFocusChangeListener = listener
    }

    private fun setupInitialTouristBlock() {
        val firstTouristInfoBlock = binding.touristInformationBlock
        val touristBlockBinding = TouristInfoBlockBinding.bind(firstTouristInfoBlock)
        setupOpenCloseButtonListener(touristBlockBinding)
    }

    private fun initAddTouristButton() {
        binding.touristAddButton.setOnClickListener {
            addNewTouristBlock()
        }
    }

    private fun addNewTouristBlock() {
        val touristBlockBinding = createTouristInfoBlock()
        updateTouristTitle(touristBlockBinding, ++touristCount)
        setLayoutTopMargin(touristBlockBinding.root)
        addTouristBlockToContainer(touristBlockBinding.root)
        updateLayoutConstraints(touristBlockBinding.root)
        setupOpenCloseButtonListener(touristBlockBinding)
        lastAddedView = touristBlockBinding.root
        setInitialVisibility(touristBlockBinding, true)
    }

    private fun setInitialVisibility(
        touristBlockBinding: TouristInfoBlockBinding,
        isVisible: Boolean
    ) {
        with(touristBlockBinding) {
            val textInputLayouts = listOf(
                etName, etSecondName,
                etCitizenship, etBirthDate, etPassportNumber, etPassportExpiringDate
            )
            textInputLayouts.forEach { layout ->
                layout.visibility = if (isVisible) View.VISIBLE else View.GONE
            }
            openButtonArrow.rotation = if (isVisible) 0f else 180f
        }
    }

    private fun setupOpenCloseButtonListener(touristBlockBinding: TouristInfoBlockBinding) {
        with(touristBlockBinding) {
            openButton.setOnClickListener {
                val isCurrentlyVisible = etName.visibility == View.VISIBLE
                animateVisibility(this, !isCurrentlyVisible)
                animateArrowRotation(openButtonArrow, !isCurrentlyVisible)
            }
        }
    }

    private fun animateVisibility(
        touristBlockBinding: TouristInfoBlockBinding,
        isVisible: Boolean
    ) {
        with(touristBlockBinding) {
            val textInputLayouts = listOf(
                etName, etSecondName, etCitizenship, etBirthDate,
                etPassportNumber, etPassportExpiringDate
            )

            TransitionManager.beginDelayedTransition(binding.dynamicContainerForNewTourist,
                AutoTransition().apply {
                    duration = 300
                })

            textInputLayouts.forEach { layout ->
                layout.visibility = if (isVisible) View.VISIBLE else View.GONE
            }
        }
    }

    private fun animateArrowRotation(arrowView: ImageView, isExpanded: Boolean) {
        val rotationAngle = if (isExpanded) 0f else 180f
        ObjectAnimator.ofFloat(arrowView, "rotation", rotationAngle).apply {
            duration = 300
            start()
        }
    }

    private fun updateLayoutConstraints(touristInfoBlock: View) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.dynamicContainerForNewTourist)
        constraintSet.connect(
            touristInfoBlock.id,
            ConstraintSet.TOP,
            lastAddedView?.id ?: ConstraintSet.PARENT_ID,
            if (lastAddedView == null) ConstraintSet.TOP else ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(binding.dynamicContainerForNewTourist)
    }

    private fun addTouristBlockToContainer(touristInfoBlock: View) {
        binding.dynamicContainerForNewTourist.addView(touristInfoBlock)
    }

    private fun setLayoutTopMargin(touristInfoBlock: View) {
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = (8 * resources.displayMetrics.density).toInt()
        }
        touristInfoBlock.layoutParams = layoutParams
    }

    private fun updateTouristTitle(
        touristBlockBinding: TouristInfoBlockBinding,
        touristCount: Int
    ) {
        touristBlockBinding.touristInformationBlockTitle.text = getOrdinalTourist(touristCount)
    }

    private fun createTouristInfoBlock(): TouristInfoBlockBinding {
        val touristInfoBlockBinding = TouristInfoBlockBinding.inflate(
            LayoutInflater.from(requireContext()),
            binding.dynamicContainerForNewTourist,
            false
        )
        if (touristInfoBlockBinding.root.id == View.NO_ID) {
            touristInfoBlockBinding.root.id = View.generateViewId()
        }
        return touristInfoBlockBinding
    }

    private fun initBackButton() {
        binding.backButton.setOnClickListener {
            (activity as? Navigator)?.navigateFromBookingToRoomList()
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
}