package com.neocaptainnemo.cv.core.locale

import android.content.Context
import android.os.Build
import java.util.*

internal class LocaleServiceImpl(private val context: Context) : LocaleService {

    override val locale: String
        get() {

            val currentLocale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.resources.configuration.locales.get(0)
            } else {
                context.resources.configuration.locale
            }

            return if (currentLocale.language == "ru") {
                "ru"
            } else {
                "en"
            }
        }
}
