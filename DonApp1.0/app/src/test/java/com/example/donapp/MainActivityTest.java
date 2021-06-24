package com.example.donapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

public class MainActivityTest {

    MainActivity myObjectUnderTest;

    public static final String FAKE_EMAIL = "rileyram@gmail.com";
    public static final String FAKE_USERNAME = "rileyram";
    public static final String FAKE_PASSWORD_VALID = "Ivysaur1223";
    public static final String FAKE_PASSWORD_INVALID = "ivy";

    @Before
    public void onLaunch() {
        myObjectUnderTest = new MainActivity();
    }


    @Test
    public void password_Valid_Test() {
        boolean result = myObjectUnderTest.isValid(FAKE_PASSWORD_VALID);

        assertTrue(result);
    }

    @Test
    public void password_Invalid_Test() {
        boolean result = myObjectUnderTest.isValid(FAKE_PASSWORD_INVALID);

        assertTrue(result);
    }

    @After
    public void tearDown() {
        myObjectUnderTest = null;
    }

}
