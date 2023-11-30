package com.test.application.room_list

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.application.core.domain.Room
import com.test.application.core.navigation.Navigator
import com.test.application.core.utilities.AppState
import com.test.application.core.view.BaseFragment
import com.test.application.room_list.databinding.FragmentRoomListBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomListFragment : BaseFragment<AppState, List<Room>, FragmentRoomListBinding>(
    FragmentRoomListBinding::inflate
) {

    private val model: RoomListViewModel by viewModel()

    private lateinit var roomListAdapter: RoomListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        initButtons()
        initRecyclerView()
        super.onViewCreated(view, savedInstanceState)
        val hotelName = arguments?.getString("hotelName")
        initFragmentTitle(hotelName)
    }

    private fun initFragmentTitle(hotelName: String?) {
        binding.tvHotelName.text = hotelName ?: getString(R.string.unknown_price)
    }

    private fun initButtons() {
        binding.backButton.setOnClickListener {
            (activity as? Navigator)?.navigateFromRoomListToHomeFragment()
        }
    }

    private fun initRecyclerView() {
        val recyclerView = binding.roomRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        roomListAdapter = RoomListAdapter(requireContext())
        recyclerView.adapter = roomListAdapter
        roomListAdapter.listener = {
            (activity as? Navigator)?.navigateFromRoomListToBooking()
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
        model.loadRoomsInfo()
    }

    override fun findProgressBar(): FrameLayout {
        return binding.progressBar
    }

    override fun setupData(data: List<Room>) {
        roomListAdapter.updateData(data)
    }
}