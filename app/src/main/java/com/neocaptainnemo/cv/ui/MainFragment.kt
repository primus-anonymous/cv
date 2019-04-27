package com.neocaptainnemo.cv.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.services.AnalyticsService
import com.neocaptainnemo.cv.services.IAnalyticsService
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    private val analyticsService: IAnalyticsService  by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)
        bottomNavigation.setupWithNavController(navController)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, Navigation.findNavController(requireActivity(), R.id.navHostFragment))
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val event = when (destination.id) {
                R.id.contactsFragment -> AnalyticsService.contactsClicked
                R.id.projectsFragment -> AnalyticsService.projectClicked
                R.id.commonFragment -> AnalyticsService.commonClicked
                else -> null
            }

            event?.let {
                analyticsService.log(it)
            }
        }

    }
}