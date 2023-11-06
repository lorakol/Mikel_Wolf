package com.training.unittestingstarter.util;

import static java.lang.annotation.RetentionPolicy.SOURCE;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

public class PersonFormatUtil {
    @Retention(SOURCE)
    @IntDef({FORMAT_FIRST_LAST, FORMAT_LAST_FIRST})
    public @interface NameFormat {}

    public static final int FORMAT_FIRST_LAST = 0x01010;
    public static final int FORMAT_LAST_FIRST = 0x01011;

    @Retention(SOURCE)
    @IntDef({FORMAT_ALL_DASHES, FORMAT_WITH_PARENS, FORMAT_WITH_SPACES})
    public @interface PhoneFormat {}
    public static final int FORMAT_ALL_DASHES = 0x02010;
    public static final int FORMAT_WITH_PARENS = 0x02011;
    public static final int FORMAT_WITH_SPACES = 0x02012;


    public static String formatName(@NameFormat int _format, String _firstName, String _lastName) {
        if(_format == FORMAT_FIRST_LAST) {
            return _firstName + " " + _lastName;
        } else if(_format == FORMAT_LAST_FIRST) {
            return _lastName + ", " + _firstName;
        }

        return _firstName + " " + _lastName;  // default case, though you might consider throwing an exception instead
    }

    public static String formatPhoneNumber(@PhoneFormat int _format, String _phone) {
        String cleanPhone = unformatPhoneNumber(_phone);

        if(cleanPhone.length() != 10) return _phone; // Incorrect length

        if(_format == FORMAT_ALL_DASHES) {
            return cleanPhone.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");
        } else if(_format == FORMAT_WITH_PARENS) {
            return cleanPhone.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "($1)$2-$3");
        } else if(_format == FORMAT_WITH_SPACES) {
            return cleanPhone.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "$1 $2 $3");
        }

        return _phone;
    }

    public static String unformatPhoneNumber(String _phone) {
        return _phone.replaceAll("[^\\d]", "");
    }

    public static boolean isPhoneNumberValid(String _phone) {
        String cleanPhone = unformatPhoneNumber(_phone);
        return cleanPhone.length() == 10;
    }
}
