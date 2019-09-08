package com.neocaptainnemo.cv.ui.projects

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.core.widget.NestedScrollView
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.neocaptainnemo.cv.*
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.services.AnalyticsEvent
import com.neocaptainnemo.cv.services.IAnalyticsService
import com.neocaptainnemo.cv.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_project_details.*
import org.koin.android.ext.android.inject


class ProjectDetailsFragment : BaseFragment(R.layout.fragment_project_details) {

    private val analyticsService: IAnalyticsService by inject()

    private val args: ProjectDetailsFragmentArgs by navArgs()

    private lateinit var project: Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
        project = args.project
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.inflateMenu(R.menu.project_details)
        toolbar.setNavigationIcon(R.drawable.ic_back)

        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.mainNavHostFragment).popBackStack()
        }

        val item = toolbar.menu.findItem(R.id.menu_item_share)

        val shareActionProvider = MenuItemCompat.getActionProvider(item) as ShareActionProvider

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"

        val stringBuilder = StringBuilder()
        stringBuilder.append(getString(R.string.project))
        stringBuilder.append(' ')
        stringBuilder.append(project.name)

        if (project.storeUrl.isNullOrEmpty().not()) {
            stringBuilder.append(' ')
            stringBuilder.append(project.storeUrl)
        }

        if (project.gitHub.isNullOrEmpty().not()) {
            stringBuilder.append(' ')
            stringBuilder.append(getString(R.string.code))
            stringBuilder.append(' ')
            stringBuilder.append(project.gitHub)
        }

        shareIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString())
        shareActionProvider.setShareIntent(shareIntent)

        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_item_share) {
                analyticsService.log(AnalyticsEvent.PROJECT_SHARED_CLICKED)
                return@setOnMenuItemClickListener false
            }

            true
        }

        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))

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

            analyticsService.log(AnalyticsEvent.PROJECT_CODE_CLICKED)

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(project.gitHub))
            try {
                startActivity(intent)

            } catch (exception: ActivityNotFoundException) {
                //do nothing
            }
        }

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

        detailsDescription.text = project.description.spanned

        duties.text = project.duties.spanned

        stack.text = project.stack.spanned

        if (project.gitHub.isNullOrEmpty().not()) {

            sourceCode.visible()
            sourceCodeTitle.visible()

            sourceCode.text = project.gitHub

        } else {
            sourceCode.gone()
            sourceCodeTitle.gone()
        }

        store.visibleIf { project.storeUrl.isNullOrEmpty().not() }

        store.setOnClickListener {

            analyticsService.log(AnalyticsEvent.PROJECT_STORE_CLICKED)

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(project.storeUrl))
            try {
                startActivity(intent)

            } catch (exception: ActivityNotFoundException) {

                Snackbar
                        .make(coordinatorLayout, R.string.cant_help_it, Snackbar.LENGTH_LONG).show()

            }
        }
    }


    companion object {

        const val ICON_TRANSITION = "ICON_TRANSITION"
        const val PLATFORM_TRANSITION = "PLATFORM_TRANSITION"
    }

}
