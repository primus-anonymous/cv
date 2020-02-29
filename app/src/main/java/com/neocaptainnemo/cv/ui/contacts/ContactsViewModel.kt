package com.neocaptainnemo.cv.ui.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.services.IDataService
import com.neocaptainnemo.cv.ui.adapter.AdapterItem
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class ContactsViewModel(private val dataService: IDataService) : ViewModel() {

    private val _progress: MutableLiveData<Boolean> = MutableLiveData(false)

    val progress: LiveData<Boolean> = _progress

    fun contacts(): LiveData<List<AdapterItem>> = dataService.contacts()
            .onStart {
                _progress.value = true

            }.onEach {
                _progress.value = false
            }
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

                it.github?.let {
                    sections.add(ContactSection(ContactType.GIT_HUB, R.string.action_github, R.string.tap_to_view_github, R.drawable.ic_link_black_24px, it))
                }

                it.cvUrl?.let {
                    sections.add(ContactSection(ContactType.CV, R.string.action_cv, R.string.tap_to_save_cv, R.drawable.ic_save_white_24px, it))
                }

                return@map mutableListOf<AdapterItem>(header).apply { addAll(sections) }.toList()

            }
            .catch {
                emit(listOf())
                _progress.value = false
            }.asLiveData()
}