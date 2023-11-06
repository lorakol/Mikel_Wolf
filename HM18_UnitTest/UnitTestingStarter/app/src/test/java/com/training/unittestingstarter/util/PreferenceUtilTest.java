package com.training.unittestingstarter.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PreferenceUtilTest {

    @Mock
    private Context mockContext;

    @Mock
    private SharedPreferences mockSharedPreferences;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mockContext.getSharedPreferences(any(String.class), any(Integer.class))).thenReturn(mockSharedPreferences);
    }

    @Test
    public void testNameFormatPreference() {
        when(mockSharedPreferences.getInt(PreferenceUtil.PREF_NAME_FORMAT, 0)).thenReturn(0);

        int result = PreferenceUtil.getNameFormat(mockContext);
        assertEquals(PersonFormatUtil.FORMAT_FIRST_LAST, result);

        when(mockSharedPreferences.getInt(PreferenceUtil.PREF_NAME_FORMAT, 0)).thenReturn(1);
        result = PreferenceUtil.getNameFormat(mockContext);
        assertEquals(PersonFormatUtil.FORMAT_LAST_FIRST, result);
    }

    @Test
    public void testPhoneFormatPreference() {
        when(mockSharedPreferences.getInt(PreferenceUtil.PREF_NUMBER_FORMAT, 0)).thenReturn(0);
        int result = PreferenceUtil.getPhoneFormat(mockContext);
        assertEquals(PersonFormatUtil.FORMAT_ALL_DASHES, result);

        when(mockSharedPreferences.getInt(PreferenceUtil.PREF_NUMBER_FORMAT, 0)).thenReturn(1);
        result = PreferenceUtil.getPhoneFormat(mockContext);
        assertEquals(PersonFormatUtil.FORMAT_WITH_PARENS, result);

        when(mockSharedPreferences.getInt(PreferenceUtil.PREF_NUMBER_FORMAT, 0)).thenReturn(2);
        result = PreferenceUtil.getPhoneFormat(mockContext);
        assertEquals(PersonFormatUtil.FORMAT_WITH_SPACES, result);
    }
}
