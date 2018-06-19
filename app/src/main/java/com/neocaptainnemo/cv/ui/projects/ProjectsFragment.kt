package com.neocaptainnemo.cv.ui.projects

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.support.v7.widget.GridLayoutManager
import android.view.*
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.daggerInject
import com.neocaptainnemo.cv.model.Filter
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.services.AnalyticsService
import com.neocaptainnemo.cv.services.IAnalyticsService
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_projects.*
import javax.inject.Inject

class ProjectsFragment : Fragment() {

    @Inject
    lateinit var adapter: ProjectsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var analyticsService: IAnalyticsService

    private lateinit var viewModel: ProjectsViewModel

    private val compositeDisposable = CompositeDisposable()


    override fun onAttach(context: Context?) {

        daggerInject()

        super.onAttach(context)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProjectsViewModel::class.java)

        setHasOptionsMenu(true)

        adapter.onProjectClicked = { project: Project, transitionView: View, transitionView2: View ->

            val intent = Intent(context, ProjectDetailsActivity::class.java)
            intent.putExtra(ProjectDetailsActivity.projectKey, project)

            analyticsService.log(AnalyticsService.projectClicked)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                transitionView.transitionName = ProjectDetailsActivity.ICON_TRANSITION
                transitionView2.transitionName = ProjectDetailsActivity.PLATFORM_TRANSITION

                val pair1 = Pair.create(transitionView, transitionView.transitionName)
                val pair2 = Pair.create(transitionView2, transitionView2.transitionName)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, pair1, pair2)
                context!!.startActivity(intent, options.toBundle())
            } else {
                context!!.startActivity(intent)
            }

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_projects, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projects.layoutManager = GridLayoutManager(context,
                context!!.resources.getInteger(R.integer.project_columns))
        projects.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) = inflater!!.inflate(R.menu.fragment_projects, menu)


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_all -> {
                item.isChecked = true

                viewModel.filter = Filter.ALL
                return true
            }

            R.id.action_android -> {
                item.isChecked = true

                viewModel.filter = Filter.ANDROID
                return true
            }
            R.id.action_ios -> {
                item.isChecked = true

                viewModel.filter = Filter.IOS
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()

        compositeDisposable.add(viewModel.progress().subscribe {
            projectsProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        compositeDisposable.add(viewModel.empty().subscribe {
            projectsEmpty.visibility = if (it) View.VISIBLE else View.GONE
        })

        compositeDisposable.add(viewModel.projects().subscribe({

            adapter.clear()
            adapter.add(it)

            adapter.notifyDataSetChanged()
        }, {

        }))

    }

    override fun onStop() {
        super.onStop()

        compositeDisposable.clear()
    }


    companion object {

        const val tag = "ProjectsFragment"

        fun instance(): ProjectsFragment = ProjectsFragment()
    }
}
