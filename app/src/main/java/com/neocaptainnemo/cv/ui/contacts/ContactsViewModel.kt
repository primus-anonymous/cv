package com.neocaptainnemo.cv.ui.contacts

import androidx.lifecycle.ViewModel
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.model.Contacts
import com.neocaptainnemo.cv.services.IDataService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ContactsViewModel @Inject constructor(dataService: IDataService) : ViewModel() {

    private val progressSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    private val source: Observable<Contacts>  by lazy {
        dataService
                .contacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .onErrorReturn { Contacts() }
                .doOnSubscribe { progressSubject.onNext(true) }
                .doOnNext { progressSubject.onNext(false) }
    }

    fun profilePic(): Observable<String> = source.map { it.userPic ?: "" }

    fun name(): Observable<String> = source.map { it.name ?: "" }

    fun profession(): Observable<String> = source.map { it.profession ?: "" }

    fun contacts(): Observable<List<ContactSection>> = source.map {

        val res = arrayListOf<ContactSection>()

        if (it.phone != null) {
            res.add(ContactSection(ContactType.PHONE, R.string.action_phone, R.string.tap_to_call, R.drawable.ic_call_black_24px, it.phone!!))
        }

        if (it.email != null) {
            res.add(ContactSection(ContactType.EMAIL, R.string.action_email, R.string.tap_to_send_email, R.drawable.ic_email_black_24px, it.email!!))
        }

        if (it.gitHub != null) {
            res.add(ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, it.gitHub!!))
        }

        if (it.cv != null) {
            res.add(ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, it.cv!!))
        }

        res
    }

    fun progress() = progressSubject

}