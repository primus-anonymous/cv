package com.neocaptainnemo.cv.services

interface ILocaleService {

    /**
     * Retrieves code of current primary, if applicable, device locale.
     */
    val locale: String
}