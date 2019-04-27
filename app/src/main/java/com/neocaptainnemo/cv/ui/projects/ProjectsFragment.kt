package com.neocaptainnemo.cv.ui.projects

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.model.Filter
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.services.AnalyticsService
import com.neocaptainnemo.cv.services.IAnalyticsService
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_projects.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ProjectsFragment : Fragment() {

    private val adapter: ProjectsAdapter = ProjectsAdapter()

    private val analyticsService: IAnalyticsService by inject()

    private val vModel: ProjectsViewModel by viewModel()

    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        projects.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context,
                context!!.resources.getInteger(R.integer.project_columns))
        projects.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) = inflater!!.inflate(R.menu.fragment_projects, menu)


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_all -> {
                item.isChecked = true

                vModel.filter = Filter.ALL
                return true
            }

            R.id.action_android -> {
                item.isChecked = true

                vModel.filter = Filter.ANDROID
                return true
            }
            R.id.action_ios -> {
                item.isChecked = true

                vModel.filter = Filter.IOS
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()

        compositeDisposable.add(vModel.progress().subscribe {
            projectsProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        compositeDisposable.add(vModel.empty().subscribe {
            projectsEmpty.visibility = if (it) View.VISIBLE else View.GONE
        })

        compositeDisposable.add(vModel.projects().subscribe({

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
