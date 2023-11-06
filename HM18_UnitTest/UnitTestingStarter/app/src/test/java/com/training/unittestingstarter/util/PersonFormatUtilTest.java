package com.training.unittestingstarter.util;

import org.junit.Test;

import static com.training.unittestingstarter.util.PersonFormatUtil.*;
import static org.junit.Assert.*;

public class PersonFormatUtilTest {

    @Test
    public void testFormatName_FirstLast() {
        String result = formatName(FORMAT_FIRST_LAST, "John", "Doe");
        assertEquals("John Doe", result);
    }

    @Test
    public void testFormatName_LastFirst() {
        String result = formatName(FORMAT_LAST_FIRST, "John", "Doe");
        assertEquals("Doe, John", result);
    }

    @Test
    public void testFormatPhoneNumber_AllDashes() {
        String result = formatPhoneNumber(FORMAT_ALL_DASHES, "4075550123");
        assertEquals("407-555-0123", result);
    }

    @Test
    public void testFormatPhoneNumber_WithParens() {
        String result = formatPhoneNumber(FORMAT_WITH_PARENS, "4075550123");
        assertEquals("(407)555-0123", result);
    }

    @Test
    public void testFormatPhoneNumber_WithSpaces() {
        String result = formatPhoneNumber(FORMAT_WITH_SPACES, "4075550123");
        assertEquals("407 555 0123", result);
    }

    @Test
    public void testUnformatPhoneNumber() {
        String result = unformatPhoneNumber("(407) 555-0123");
        assertEquals("4075550123", result);
    }

    @Test
    public void testIsPhoneNumberValid_Valid() {
        assertTrue(isPhoneNumberValid("407-555-0123"));
    }

    @Test
    public void testIsPhoneNumberValid_Invalid() {
        assertFalse(isPhoneNumberValid("40-555-0123"));
    }
}
