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
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.scrollChangeObservable
import com.neocaptainnemo.cv.services.AnalyticsEvent
import com.neocaptainnemo.cv.services.IAnalyticsService
import com.neocaptainnemo.cv.spanned
import com.neocaptainnemo.cv.ui.BaseFragment
import com.neocaptainnemo.cv.visibleIf
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_project_details.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ProjectDetailsFragment : BaseFragment(R.layout.fragment_project_details) {

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

    override fun onStart() {
        super.onStart()

        autoDispose {

            viewModel.storeVisibility.subscribe {
                store.visibleIf { it }
            }

            viewModel.gitHubVisibility.subscribe {
                sourceCode.visibleIf { it }
                sourceCodeTitle.visibleIf { it }
            }

            viewModel.platformImage.subscribe {
                platform.setImageResource(it)
            }

            viewModel.webPic.subscribe {
                val requestOption = RequestOptions().error(R.drawable.placeholder).placeholder(R.drawable.placeholder)

                Glide.with(this)
                        .applyDefaultRequestOptions(requestOption)
                        .load(it)
                        .into(projImage)
            }

            viewModel.coverPic.subscribe {
                val requestOption = RequestOptions().error(R.drawable.placeholder).placeholder(R.drawable.placeholder)

                Glide.with(this)
                        .applyDefaultRequestOptions(requestOption)
                        .load(it)
                        .into(logo)
            }

            viewModel.projectName.subscribe {
                projTitle.text = it
            }

            viewModel.stack.subscribe {
                stack.text = it.spanned
            }

            viewModel.company.subscribe {
                company.text = it
            }

            viewModel.duties.subscribe {
                duties.text = it.spanned
            }

            viewModel.detailsDescription.subscribe {
                detailsDescription.text = it.spanned
            }

            viewModel.sourceCode.subscribe {
                sourceCode.text = it
            }

            nestedScroll.scrollChangeObservable().map {
                val scrollBounds = Rect()
                nestedScroll.getHitRect(scrollBounds)
                projTitle.getLocalVisibleRect(scrollBounds)
            }.flatMap {
                if (it) Observable.just("") else viewModel.projectName
            }.subscribe {
                collapsingToolbar.title = it
            }
        }
    }


    companion object {

        const val ICON_TRANSITION = "ICON_TRANSITION"
        const val PLATFORM_TRANSITION = "PLATFORM_TRANSITION"
    }

}
