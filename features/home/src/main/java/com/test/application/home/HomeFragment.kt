package com.test.application.home

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.test.application.core.domain.AboutTheHotel
import com.test.application.core.domain.Hotel
import com.test.application.core.navigation.Navigator
import com.test.application.core.utilities.AppState
import com.test.application.common.utils.HOME_FRAGMENT_ARG
import com.test.application.common.utils.formatPrice
import com.test.application.core.view.BaseFragment
import com.test.application.home.databinding.FragmentHomeBinding
import com.test.application.common.utils.ChipInflaterManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<AppState, Hotel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val model: HomeViewModel by viewModel()
    private lateinit var chipManager: ChipInflaterManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initButtons(data: Hotel) {
        binding.bookingButton.setOnClickListener {
            val hotelName = data.name
            val bundle = bundleOf(HOME_FRAGMENT_ARG to hotelName)
            (activity as? Navigator)?.navigateToRoomListFragment(bundle)
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
        initButtons(data)
    }

    private fun initTextInformation(data: Hotel) {
        with(binding) {
            tvHotelName.text = data.name
            tvHotelAddress.text = data.address
            tvPrice.text = formatPrice(data.minimalPrice, requireContext().resources)
            tvPriceDescription.text = data.priceForIt
            ratingBlock.tvRating.text = data.rating.toString()
            ratingBlock.tvRatingName.text = data.ratingName
            tvHotelDescription.text = data.aboutTheHotel?.description
        }
    }

    private fun initPeculiaritiesChips(aboutTheHotel: AboutTheHotel?) {
        aboutTheHotel?.peculiarities?.let {peculiarities ->
            val chipGroup = binding.hotelPeculiaritiesChips
            chipManager = ChipInflaterManager(requireContext())
            val chipLayoutResId = R.layout.item_chip
            chipManager.addPeculiaritiesChips(peculiarities, chipGroup, chipLayoutResId)
        }
    }

    private fun initImages(imageUrls: List<String>?) {
        val viewPager = binding.imageCarousel
        val indicator = binding.imageIndicator

        val adapter = HotelImageAdapter(imageUrls ?: emptyList())
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)
    }

    override fun findProgressBar(): FrameLayout {
        return binding.progressBar
    }

    override fun onDestroyView() {
        chipManager.cleanup()
        super.onDestroyView()
    }
}