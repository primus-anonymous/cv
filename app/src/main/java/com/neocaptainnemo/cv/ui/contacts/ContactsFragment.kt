package com.neocaptainnemo.cv.ui.contacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.services.AnalyticsEvent
import com.neocaptainnemo.cv.services.IAnalyticsService
import com.neocaptainnemo.cv.ui.BaseFragment
import com.neocaptainnemo.cv.ui.adapter.DiffAdapter
import com.neocaptainnemo.cv.visibleIf
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {

    private val analyticsService: IAnalyticsService by inject()

    private val contactsBinder = ContactsBinder()

    private val adapter: DiffAdapter = DiffAdapter(
            listOf(
                    contactsBinder,
                    ContactsHeaderBinder()
            )
    )

    private val vModel: ContactsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contactsBinder.itemClicked = {
            when (it.type) {
                ContactType.EMAIL -> {
                    val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", it.value, null)).apply {
                        putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject))
                    }

                    startActivity(Intent.createChooser(emailIntent,
                            getString(R.string.mail_chooser)))

                    analyticsService.log(AnalyticsEvent.EMAIL_CLICKED)
                }

                ContactType.PHONE -> {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", it.value, null))
                    startActivity(intent)

                    analyticsService.log(AnalyticsEvent.PHONE_CLICKED)
                }

                ContactType.GIT_HUB -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.value))
                    startActivity(intent)

                    analyticsService.log(AnalyticsEvent.GIT_HUB_CLICKED)
                }

                ContactType.CV -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.value))
                    startActivity(intent)

                    analyticsService.log(AnalyticsEvent.CV_CLICKED)
                }


            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactsList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        autoDispose {
            vModel.progress.subscribe {
                contactsProgress.visibleIf { it }
            }
            vModel.contacts().subscribe {
                adapter.swapData(it)
            }
        }
    }
}