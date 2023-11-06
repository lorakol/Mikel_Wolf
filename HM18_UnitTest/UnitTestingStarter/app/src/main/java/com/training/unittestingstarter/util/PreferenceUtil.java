package com.training.unittestingstarter.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtil {
    public static final String PREF_NAME_FORMAT = "com.fullsail.android.PREF_NAME_FORMAT";
    public static final String PREF_NUMBER_FORMAT = "com.fullsail.android.PREF_NUMBER_FORMAT";

    @PersonFormatUtil.NameFormat
    public static int getNameFormat(Context _context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
        int nameFormat = preferences.getInt(PREF_NAME_FORMAT, 0);

        if(nameFormat == 0) {
            return PersonFormatUtil.FORMAT_FIRST_LAST;
        } else {
            return PersonFormatUtil.FORMAT_LAST_FIRST;
        }
    }

    @PersonFormatUtil.PhoneFormat
    public static int getPhoneFormat(Context _context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
        int numberFormat = preferences.getInt(PREF_NUMBER_FORMAT, 0);

        if(numberFormat == 0) {
            return PersonFormatUtil.FORMAT_ALL_DASHES;
        } else if(numberFormat == 1) {
            return PersonFormatUtil.FORMAT_WITH_PARENS;
        } else {
            return PersonFormatUtil.FORMAT_WITH_SPACES;
        }
    }
}
