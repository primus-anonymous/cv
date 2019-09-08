package com.neocaptainnemo.cv.ui.contacts

import androidx.lifecycle.ViewModel
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.services.IDataService
import com.neocaptainnemo.cv.ui.adapter.AdapterItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class ContactsViewModel(private val dataService: IDataService) : ViewModel() {

    private val progressSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)


    val contacts: Observable<List<AdapterItem>>
        get() = dataService
                .contacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progressSubject.onNext(true) }
                .doOnNext { progressSubject.onNext(false) }
                .doOnError { progressSubject.onNext(false) }
                .map {

                    val header = ContactsHeader(image = it.userPic ?: "", name = it.name
                            ?: "", profession = it.profession ?: "")

                    val sections = arrayListOf<ContactSection>()

                    it.phone?.let {
                        sections.add(ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, it))
                    }

                    it.email?.let {
                        sections.add(ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, it))
                    }

                    it.gitHub?.let {
                        sections.add(ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, it))
                    }

                    it.cv?.let {
                        sections.add(ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, it))
                    }

                    return@map mutableListOf<AdapterItem>(header).apply { addAll(sections) }.toList()

                }.onErrorReturn { listOf() }


    val progress = progressSubject

}