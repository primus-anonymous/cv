package com.neocaptainnemo.cv.ui.projects

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.model.Filter
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.services.AnalyticsEvent
import com.neocaptainnemo.cv.services.IAnalyticsService
import com.neocaptainnemo.cv.ui.BaseFragment
import com.neocaptainnemo.cv.ui.MainFragmentDirections
import com.neocaptainnemo.cv.ui.adapter.DiffAdapter
import com.neocaptainnemo.cv.visibleIf
import kotlinx.android.synthetic.main.fragment_projects.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ProjectsFragment : BaseFragment(R.layout.fragment_projects) {

    private val projectBinder = ProjectsBinder()

    private val adapter: DiffAdapter = DiffAdapter(listOf(projectBinder))

    private val analyticsService: IAnalyticsService by inject()

    private val vModel: ProjectsViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        projectBinder.onProjectClicked = { project: Project, transitionView: View, transitionView2: View ->

            analyticsService.log(AnalyticsEvent.PROJECT_DETAILS_CLICKED)

            val mainNavigation = Navigation.findNavController(requireActivity(), R.id.mainNavHostFragment)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                transitionView.transitionName = ProjectDetailsFragment.ICON_TRANSITION
                transitionView2.transitionName = ProjectDetailsFragment.PLATFORM_TRANSITION

                val extras = FragmentNavigatorExtras(
                        transitionView to transitionView.transitionName,
                        transitionView2 to transitionView2.transitionName)

                mainNavigation.navigate(MainFragmentDirections.projectDetailsAction(project), extras)
            } else {
                mainNavigation.navigate(MainFragmentDirections.projectDetailsAction(project))
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projects.apply {
            layoutManager = GridLayoutManager(context, requireContext().resources.getInteger(R.integer.project_columns))
            adapter = this@ProjectsFragment.adapter

        }

        toolbar.apply {
            inflateMenu(R.menu.fragment_projects)

            setOnMenuItemClickListener { item ->

                when (item.itemId) {
                    R.id.action_all -> {
                        item.isChecked = true

                        vModel.filter = Filter.ALL
                    }

                    R.id.action_android -> {
                        item.isChecked = true

                        vModel.filter = Filter.ANDROID
                    }
                    R.id.action_ios -> {
                        item.isChecked = true

                        vModel.filter = Filter.IOS
                    }
                    else -> {
                        //do nothing
                    }
                }

                true
            }
        }
    }


    override fun onStart() {
        super.onStart()

        autoDispose {
            vModel.progress.subscribe {
                projectsProgress.visibleIf { it }
            }
            vModel.empty.subscribe {
                projectsEmpty.visibleIf { it }
            }
            vModel.projects.subscribe {
                adapter.swapData(it)
            }
        }
    }

}
