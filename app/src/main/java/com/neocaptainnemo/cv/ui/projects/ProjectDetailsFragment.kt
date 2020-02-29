package com.neocaptainnemo.cv.ui.projects

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.observe
import androidx.lifecycle.switchMap
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.scrollChangeLiveData
import com.neocaptainnemo.cv.services.AnalyticsEvent
import com.neocaptainnemo.cv.services.IAnalyticsService
import com.neocaptainnemo.cv.spanned
import com.neocaptainnemo.cv.visibleIf
import kotlinx.android.synthetic.main.fragment_project_details.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ProjectDetailsFragment : Fragment(R.layout.fragment_project_details) {

    private val analyticsService: IAnalyticsService by inject()

    private val args: ProjectDetailsFragmentArgs by navArgs()

    private val viewModel: ProjectDetailsViewModel by viewModel { parametersOf(args.project) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            projImage.transitionName = ICON_TRANSITION
            platform.transitionName = PLATFORM_TRANSITION
        }

        toolbar.apply {
            inflateMenu(R.menu.project_details)
            setNavigationIcon(R.drawable.ic_back)

            setNavigationOnClickListener {
                Navigation.findNavController(requireActivity(), R.id.mainNavHostFragment).popBackStack()
            }

            setOnMenuItemClickListener {
                if (it.itemId == R.id.menu_item_share) {
                    analyticsService.log(AnalyticsEvent.PROJECT_SHARED_CLICKED)
                    return@setOnMenuItemClickListener false
                }

                true
            }
        }

        val item = toolbar.menu.findItem(R.id.menu_item_share)

        (MenuItemCompat.getActionProvider(item) as? ShareActionProvider)?.apply {
            setShareIntent(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, viewModel.shareUrl)
            })
        }

        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))


        viewModel.storeVisibility.observe(viewLifecycleOwner) {
            store.visibleIf { it }
        }

        viewModel.gitHubVisibility.observe(viewLifecycleOwner) {
            sourceCode.visibleIf { it }
            sourceCodeTitle.visibleIf { it }
        }

        viewModel.platformImage.observe(viewLifecycleOwner) {
            platform.setImageResource(it)
        }

        viewModel.webPic.observe(viewLifecycleOwner) {
            val requestOption = RequestOptions().error(R.drawable.placeholder).placeholder(R.drawable.placeholder)

            Glide.with(this)
                    .applyDefaultRequestOptions(requestOption)
                    .load(it)
                    .into(projImage)
        }

        viewModel.coverPic.observe(viewLifecycleOwner) {
            val requestOption = RequestOptions().error(R.drawable.placeholder).placeholder(R.drawable.placeholder)

            Glide.with(this)
                    .applyDefaultRequestOptions(requestOption)
                    .load(it)
                    .into(logo)
        }

        viewModel.projectName.observe(viewLifecycleOwner) {
            projTitle.text = it
        }

        viewModel.stack.observe(viewLifecycleOwner) {
            stack.text = it.spanned
        }

        viewModel.company.observe(viewLifecycleOwner) {
            company.text = it
        }

        viewModel.duties.observe(viewLifecycleOwner) {
            duties.text = it.spanned
        }

        viewModel.detailsDescription.observe(viewLifecycleOwner) {
            detailsDescription.text = it.spanned
        }

        viewModel.sourceCode.observe(viewLifecycleOwner) {
            sourceCode.text = it
        }

        nestedScroll.scrollChangeLiveData().map {
            val scrollBounds = Rect()
            nestedScroll.getHitRect(scrollBounds)
            projTitle.getLocalVisibleRect(scrollBounds)
        }.switchMap {
            if (it) MutableLiveData("") else viewModel.projectName
        }.observe(viewLifecycleOwner) {
            collapsingToolbar.title = it
        }

        sourceCode.setOnClickListener {

            analyticsService.log(AnalyticsEvent.PROJECT_CODE_CLICKED)

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.gitHubUrl))
            try {
                startActivity(intent)

            } catch (exception: ActivityNotFoundException) {
                //do nothing
            }
        }

        store.setOnClickListener {

            analyticsService.log(AnalyticsEvent.PROJECT_STORE_CLICKED)

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.storeUrl))
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
