package com.neocaptainnemo.cv.core.locale

import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

internal class LocaleServiceImpl @Inject constructor(@ApplicationContext private val context: Context) : LocaleService {

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
