package com.neocaptainnemo.cv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neocaptainnemo.cv.services.AnalyticsService
import com.neocaptainnemo.cv.services.IAnalyticsService
import com.neocaptainnemo.cv.ui.common.CommonFragment
import com.neocaptainnemo.cv.ui.contacts.ContactsFragment
import com.neocaptainnemo.cv.ui.projects.ProjectsFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private var selectedSection = 0

    private val analyticsService: IAnalyticsService by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        openScreen(savedInstanceState?.getInt(sectionkey) ?: R.id.title_contacts)

        bottomNavigation.setOnNavigationItemSelectedListener {

            openScreen(it.itemId)

            true
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(sectionkey, selectedSection)
    }

    private fun openScreen(itemId: Int) {
        when (itemId) {
            R.id.action_projects -> {
                if (selectedSection != R.id.action_projects) {
                    showProjects()

                    analyticsService.log(AnalyticsService.projectsClicked)
                }
            }

            R.id.action_common -> {
                if (selectedSection != R.id.action_common) {
                    showCommon()

                    analyticsService.log(AnalyticsService.commonClicked)
                }
            }

            R.id.title_contacts -> {
                if (selectedSection != R.id.title_contacts) {
                    showContacts()

                    analyticsService.log(AnalyticsService.contactsClicked)

                }
            }

        }

    }

    private fun showProjects() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_main, ProjectsFragment.instance(), ProjectsFragment.tag)
                .commit()
        supportActionBar?.setTitle(R.string.projects)
        selectedSection = R.id.action_projects
    }


    private fun showCommon() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_main, CommonFragment.instance(), CommonFragment.tag)
                .commit()
        supportActionBar?.setTitle(R.string.action_common)
        selectedSection = R.id.action_common
    }

    private fun showContacts() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_main, ContactsFragment.instance(), ContactsFragment.tag)
                .commit()
        supportActionBar?.setTitle(R.string.title_contacts)
        selectedSection = R.id.title_contacts
    }

    companion object {
        const val sectionkey = "section"
    }


}
