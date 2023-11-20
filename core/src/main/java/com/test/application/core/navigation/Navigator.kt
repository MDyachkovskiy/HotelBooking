package com.test.application.core.navigation

import android.os.Bundle

interface Navigator {
    fun navigateToRoomListFragment(bundle: Bundle)
    fun navigateFromRoomListToHomeFragment()
}