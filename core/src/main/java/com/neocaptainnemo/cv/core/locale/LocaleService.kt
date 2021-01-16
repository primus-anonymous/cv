package com.neocaptainnemo.cv.core.locale

interface LocaleService {

    /**
     * Retrieves code of current primary, if applicable, device locale.
     */
    val locale: String
}