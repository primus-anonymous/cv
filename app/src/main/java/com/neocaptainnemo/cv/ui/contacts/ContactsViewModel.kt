package com.neocaptainnemo.cv.ui.contacts

import androidx.lifecycle.ViewModel
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.core.data.DataService
import kotlinx.coroutines.flow.*

class ContactsViewModel(private val dataService: DataService) : ViewModel() {

    private val _progress = MutableStateFlow(false)

    val progress: Flow<Boolean> = _progress

    fun contacts(): Flow<List<Any>> = dataService
            .contacts()
            .onStart {
                _progress.value = true

            }
            .onEach {
                _progress.value = false
            }
            .map {
                val header = ContactsHeader(image = it.userPic ?: "",
                                            name = it.name ?: "",
                                            profession = it.profession ?: "")

                val sections = arrayListOf<ContactSection>()

                it.phone?.let {
                    sections.add(ContactSection(ContactType.PHONE,
                                                R.string.action_phone,
                                                R.string.tap_to_call,
                                                R.drawable.ic_call,
                                                it))
                }

                it.telegram?.let {
                    sections.add(ContactSection(ContactType.TELEGRAM,
                                                R.string.action_telegram,
                                                R.string.tap_to_open_telegram,
                                                R.drawable.ic_telegram,
                                                it))
                }

                it.email?.let {
                    sections.add(ContactSection(ContactType.EMAIL,
                                                R.string.action_email,
                                                R.string.tap_to_send_email,
                                                R.drawable.ic_email,
                                                it))
                }

                it.github?.let {
                    sections.add(ContactSection(ContactType.GIT_HUB,
                                                R.string.action_github,
                                                R.string.tap_to_view_github,
                                                R.drawable.ic_github,
                                                it))
                }

                it.cvUrl?.let {
                    sections.add(ContactSection(ContactType.CV,
                                                R.string.action_cv,
                                                R.string.tap_to_save_cv,
                                                R.drawable.ic_cv,
                                                it))
                }

                return@map mutableListOf<Any>(header)
                        .apply { addAll(sections) }
                        .toList()

            }
            .catch {
                emit(listOf())
                _progress.value = false
            }
}