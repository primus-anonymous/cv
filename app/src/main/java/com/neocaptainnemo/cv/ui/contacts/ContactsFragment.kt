package com.neocaptainnemo.cv.ui.contacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.services.AnalyticsService
import com.neocaptainnemo.cv.services.IAnalyticsService
import com.neocaptainnemo.cv.ui.BaseFragment
import com.neocaptainnemo.cv.ui.adapter.AdapterItem
import com.neocaptainnemo.cv.ui.adapter.DiffAdapter
import com.neocaptainnemo.cv.visibleIf
import io.reactivex.Observable
import io.reactivex.functions.Function4
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ContactsFragment : BaseFragment() {

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
                            "mailto", it.value, null))
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject))
                    startActivity(Intent.createChooser(emailIntent, getString(R.string.mail_chooser)))

                    analyticsService.log(AnalyticsService.emailClicked)
                }

                ContactType.PHONE -> {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", it.value, null))
                    startActivity(intent)

                    analyticsService.log(AnalyticsService.phoneClicked)
                }

                ContactType.GIT_HUB -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.value))
                    startActivity(intent)

                    analyticsService.log(AnalyticsService.gitHubClicked)
                }

                ContactType.CV -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.value))
                    startActivity(intent)

                    analyticsService.log(AnalyticsService.cvClicked)
                }


            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_contacts, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactsList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        autoDispose {
            vModel.progress().subscribe {
                contactsProgress.visibleIf { it }
            }
        }

        autoDispose {
            Observable.combineLatest(
                    vModel.name(),
                    vModel.profession(),
                    vModel.profilePic(),
                    vModel.contacts(),
                    Function4<String, String, String, List<ContactSection>, Pair<ContactsHeader, List<ContactSection>>> { name, profession, pic, contacts ->

                        ContactsHeader(image = pic, name = name, profession = profession) to contacts

                    }).subscribe { res ->

                adapter.swapData(arrayListOf<AdapterItem>(res.first).also {
                    it.addAll(res.second)
                })
            }
        }

    }
}