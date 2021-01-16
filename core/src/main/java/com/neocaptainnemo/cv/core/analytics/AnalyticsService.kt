package com.neocaptainnemo.cv.core.analytics

interface AnalyticsService {

    /**
     * Logs some triggered event.
     * @param event - triggered event.
     */
    fun log(event: AnalyticsEvent)

}

enum class AnalyticsEvent(val eventValue: String) {

    CONTACTS_CLICKED("contactsClicked"),
    PROJECTS_CLICKED("projectsClicked"),
    PROJECT_DETAILS_CLICKED("projectDetailsClicked"),
    COMMON_CLICKED("commonClicked"),
    PHONE_CLICKED("phoneClicked"),
    EMAIL_CLICKED("emailClicked"),
    GIT_HUB_CLICKED("gitHubClicked"),
    CV_CLICKED("cvClicked"),
    TELEGRAM_CLICKED("telegramClicked"),
    PROJECT_CODE_CLICKED("projectSourceCodeClicked"),
    PROJECT_STORE_CLICKED("projectSoreClicked"),
    PROJECT_SHARED_CLICKED("projectShareClicked")
}