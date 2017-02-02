package com.neocaptainnemo.cv;

import android.content.Context;
import android.os.Build;

import java.util.Locale;

public class LocaleService {

    private static final LocaleService instance = new LocaleService();

    private LocaleService() {
        //do nothing
    }

    public static LocaleService getInstance() {
        return instance;
    }

    public String getLocale(Context context) {
        Locale currentLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            currentLocale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            currentLocale = context.getResources().getConfiguration().locale;
        }

        if (currentLocale.getLanguage().equals("ru")) {
            return "ru";
        } else {
            return "en";
        }

    }

}
