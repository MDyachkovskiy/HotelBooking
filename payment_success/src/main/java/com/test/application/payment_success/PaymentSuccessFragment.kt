package com.test.application.payment_success

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.application.core.navigation.Navigator
import com.test.application.payment_success.databinding.FragmentPaymentSuccessBinding
import kotlin.random.Random

class PaymentSuccessFragment : Fragment() {

    private var _binding: FragmentPaymentSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOrderDescribeText()
        setupButtons()
    }

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            (activity as? Navigator)?.navigateFromPaymentSuccessToBooking()
        }
        binding.exitButton.setOnClickListener {
            (activity as? Navigator)?.navigateFromPaymentSuccessToHome()
        }
    }

    private fun initOrderDescribeText() {
        val orderNumber = Random.nextInt(100000, 999999)
        val orderText = getString(R.string.order_confirmation, orderNumber)
        binding.tvOrderDescribingText.text = orderText
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}