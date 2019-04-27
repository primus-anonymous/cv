package com.neocaptainnemo.cv.ui.projects

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import android.text.Html
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.daggerInject
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.services.AnalyticsService
import com.neocaptainnemo.cv.services.IAnalyticsService
import kotlinx.android.synthetic.main.activity_project_details.*
import javax.inject.Inject


class ProjectDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var analyticsService: IAnalyticsService

    private lateinit var project: Project

    override fun onCreate(savedInstanceState: Bundle?) {

        daggerInject()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_project_details)

        project = intent.getParcelableExtra(projectKey)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))

        nestedScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, _, _, _ ->
            val scrollBounds = Rect()
            nestedScroll.getHitRect(scrollBounds)
            if (projTitle.getLocalVisibleRect(scrollBounds)) {

                collapsingToolbar.title = ""
            } else {
                collapsingToolbar.title = project.name

            }
        })

        sourceCode.setOnClickListener {

            analyticsService.log(AnalyticsService.projectSourceCodeClicked)

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(project.gitHub))
            try {
                startActivity(intent)

            } catch (exception: ActivityNotFoundException) {
                //do nothing
            }
        }


        ViewCompat.setTransitionName(appBar, "extra_image")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            projImage.transitionName = ICON_TRANSITION
            platform.transitionName = PLATFORM_TRANSITION
        }

        val requestOption = RequestOptions().error(R.drawable.placeholder).placeholder(R.drawable.placeholder)

        Glide.with(this)
                .applyDefaultRequestOptions(requestOption)
                .load(project.coverPic)
                .into(logo)

        Glide.with(this)
                .applyDefaultRequestOptions(requestOption)
                .load(project.webPic)
                .into(projImage)

        projTitle.text = project.name
        company.text = project.vendor

        platform.setImageResource(if (project.platform == Project.PLATFORM_ANDROID) {
            R.drawable.ic_android
        } else {
            R.drawable.ic_apple
        })

        detailsDescription.text =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(project.description, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    Html.fromHtml(project.description)
                }

        duties.text =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(project.duties, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    Html.fromHtml(project.duties)
                }


        stack.text =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(project.stack, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    Html.fromHtml(project.stack)
                }

        if (project.gitHub?.isNotEmpty() == true) {

            sourceCode.visibility = View.VISIBLE
            sourceCodeTitle.visibility = View.VISIBLE

            sourceCode.text = project.gitHub

        } else {
            sourceCode.visibility = View.GONE
            sourceCodeTitle.visibility = View.GONE
        }

        store.visibility = if (project.storeUrl?.isNotEmpty() == true) View.VISIBLE else View.GONE

        store.setOnClickListener { _ ->

            analyticsService.log(AnalyticsService.projectStoreClicked)

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(project.storeUrl))
            try {
                startActivity(intent)

            } catch (exception: ActivityNotFoundException) {

                Snackbar
                        .make(coordinatorLayout, R.string.cant_help_it, Snackbar.LENGTH_LONG).show()

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_project_details, menu)

        val item = menu.findItem(R.id.menu_item_share)

        val shareActionProvider = MenuItemCompat.getActionProvider(item) as ShareActionProvider

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"

        val stringBuilder = StringBuilder()
        stringBuilder.append(getString(R.string.project))
        stringBuilder.append(' ')
        stringBuilder.append(project.name)

        if (!TextUtils.isEmpty(project.storeUrl)) {
            stringBuilder.append(' ')
            stringBuilder.append(project.storeUrl)
        }

        if (!TextUtils.isEmpty(project.gitHub)) {
            stringBuilder.append(' ')
            stringBuilder.append(getString(R.string.code))
            stringBuilder.append(' ')
            stringBuilder.append(project.gitHub)
        }


        shareIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString())

        shareActionProvider.setShareIntent(shareIntent)

        analyticsService.log(AnalyticsService.projectShareClicked)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ICON_TRANSITION = "ICON_TRANSITION"
        const val PLATFORM_TRANSITION = "PLATFORM_TRANSITION"

        const val projectKey = "project"
    }

}
