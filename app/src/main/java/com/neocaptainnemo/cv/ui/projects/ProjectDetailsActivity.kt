package com.neocaptainnemo.cv.ui.projects

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.neocaptainnemo.cv.core.analytics.AnalyticsEvent
import com.neocaptainnemo.cv.core.analytics.AnalyticsService
import com.neocaptainnemo.cv.core.model.Project
import com.neocaptainnemo.cv.ui.projects.compose.ProjectDetailsScreen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProjectDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PROJECT = "EXTRA_PROJECT"

        fun show(
                context: Context,
                project: Project,
        ) {
            Intent(context,
                   ProjectDetailsActivity::class.java).apply {
                putExtra(EXTRA_PROJECT, project)
            }
                    .also {
                        context.startActivity(it)
                    }
        }
    }

    private val analyticsService: AnalyticsService by inject()

    private val viewModel: ProjectDetailsViewModel by viewModel {
        parametersOf(intent.getParcelableExtra<Project>(EXTRA_PROJECT))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProjectDetailsScreen(viewModel = viewModel,
                                 {
                                     Intent(Intent.ACTION_VIEW, Uri.parse(it)).also {intent ->
                                         startActivity(intent)
                                     }
                                     analyticsService.log(AnalyticsEvent.PROJECT_STORE_CLICKED)
                                 },
                                 {
                                     Intent(Intent.ACTION_VIEW,
                                            Uri.parse(it)).also {intent ->
                                     startActivity(intent)
                                 }

                                     analyticsService.log(AnalyticsEvent.PROJECT_CODE_CLICKED)
                                 },
                                 {
                                     onBackPressed()
                                 },
                                 {
                                     val sendIntent = Intent().apply {
                                         action = Intent.ACTION_SEND
                                         putExtra(Intent.EXTRA_TEXT,
                                                  viewModel.shareUrl)
                                         type = "text/plain"
                                     }

                                     analyticsService.log(AnalyticsEvent.PROJECT_SHARED_CLICKED)

                                     Intent.createChooser(sendIntent,
                                                          null)
                                             .also {
                                                 startActivity(it)
                                             }

                                 })
        }
    }
}
