package com.neocaptainnemo.cv.ui.contacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.services.AnalyticsService
import com.neocaptainnemo.cv.services.IAnalyticsService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function4
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ContactsFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()

    private val analyticsService: IAnalyticsService by inject()

    private val adapter: ContactsAdapter = ContactsAdapter()

    private val vModel: ContactsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        adapter.itemClicked = {
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

        compositeDisposable.add(vModel.progress().subscribe {
            contactsProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        compositeDisposable.add(Observable.combineLatest(
                vModel.name(),
                vModel.profession(),
                vModel.profilePic(),
                vModel.contacts(),
                Function4<String, String, String, List<ContactSection>, Pair<ContactsHeader, List<ContactSection>>> { name, profession, pic, contacts ->

                    Pair(ContactsHeader(image = pic, name = name, profession = profession), contacts)

                }).subscribe {

            adapter.contactsHeader = it.first
            adapter.clear()
            adapter.add(it.second)
            adapter.notifyDataSetChanged()
        })

    }


    override fun onStop() {
        super.onStop()

        compositeDisposable.clear()
    }

    companion object {

        const val tag = "ContactsFragment"

        fun instance(): ContactsFragment = ContactsFragment()
    }

}