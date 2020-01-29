package com.neocaptainnemo.cv.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.services.AnalyticsEvent
import com.neocaptainnemo.cv.services.IAnalyticsService
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val analyticsService: IAnalyticsService by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)

        bottomNavigation.apply {
            setupWithNavController(navController)
            setOnNavigationItemSelectedListener { item ->
                NavigationUI.onNavDestinationSelected(item, Navigation.findNavController(requireActivity(), R.id.navHostFragment))
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
             when (destination.id) {
                R.id.contactsFragment -> AnalyticsEvent.CONTACTS_CLICKED
                R.id.projectsFragment -> AnalyticsEvent.PROJECTS_CLICKED
                R.id.commonFragment -> AnalyticsEvent.COMMON_CLICKED
                else -> null
            }?.also {
                analyticsService.log(it)
            }
        }

    }
}