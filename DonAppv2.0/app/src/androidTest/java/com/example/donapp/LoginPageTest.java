package com.example.donapp;

import android.app.Activity;
import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.assertion.PositionAssertions.*;
import static org.junit.Assert.*;

@LargeTest
public class LoginPageTest {


    public static final String FAKE_USERNAME = "rodneyram";
    public static final String FAKE_USERNAME_INVALID = "rodneeeeram";
    public static final String FAKE_USERNAME_TOO_SMALL = "as";
    public static final String FAKE_USERNAME_TOO_BIG = "rodneyramrodneyramrodneyram";
    public static final String FAKE_PASSWORD_VALID = "Gengar1223";
    public static final String FAKE_PASSWORD_INVALID = "gengaree";

    DatabaseHelper myDb;

    @Rule
    public ActivityTestRule<LoginPage> activityRule
            = new ActivityTestRule<>(LoginPage.class);


    @Test
    public void checkIfArranged() {

        onView(withId(R.id.loginPageTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.usernameTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.username)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.forgotPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.loginBttn)).check(matches(isDisplayed()));

        onView(withId(R.id.loginPageTitle)).check(isCompletelyAbove(withId(R.id.usernameTitle)));
        onView(withId(R.id.usernameTitle)).check(isCompletelyAbove(withId(R.id.username)));
        onView(withId(R.id.username)).check(isCompletelyAbove(withId(R.id.passwordTitle)));
        onView(withId(R.id.passwordTitle)).check(isCompletelyAbove(withId(R.id.password)));
        onView(withId(R.id.password)).check(isCompletelyAbove(withId(R.id.forgotPassword)));
        onView(withId(R.id.forgotPassword)).check(isCompletelyAbove(withId(R.id.loginBttn)));
        onView(withId(R.id.loginBttn)).check(isCompletelyAbove(withId(R.id.createAccountText)));
    }


    @Test
    public void checkLoginNoInput() {
        onView(withId(R.id.username)).perform(clearText());
        onView(withId(R.id.password)).perform(clearText());

        onView(withId(R.id.loginBttn)).perform(click());

        assertFalse(activityRule.getActivity().loginSuccessful);
    }

    @Test
    public void checkLoginWithInput() {
        onView(withId(R.id.username)).perform(replaceText(FAKE_USERNAME));
        onView(withId(R.id.password)).perform(replaceText(FAKE_PASSWORD_VALID));

        onView(withId(R.id.loginBttn)).perform(click());

        assertTrue(activityRule.getActivity().loginSuccessful);
    }

    @Test
    public void checkLoginOnlyUsername() {
        onView(withId(R.id.username)).perform(replaceText(FAKE_USERNAME));
        onView(withId(R.id.password)).perform(clearText());

        onView(withId(R.id.loginBttn)).perform(click());

        assertFalse(activityRule.getActivity().loginSuccessful);
    }

    @Test
    public void checkLoginOnlyPassword() {
        onView(withId(R.id.username)).perform(clearText());
        onView(withId(R.id.password)).perform(replaceText(FAKE_PASSWORD_VALID));

        onView(withId(R.id.loginBttn)).perform(click());

        assertFalse(activityRule.getActivity().loginSuccessful);
    }

    @Test
    public void checkLoginInvalidUsername() {
        onView(withId(R.id.username)).perform(replaceText(FAKE_USERNAME_INVALID));
        onView(withId(R.id.password)).perform(replaceText(FAKE_PASSWORD_VALID));

        onView(withId(R.id.loginBttn)).perform(click());

        assertFalse(activityRule.getActivity().loginSuccessful);
    }

    @Test
    public void checkLoginInvalidPassword() {
        onView(withId(R.id.username)).perform(replaceText(FAKE_USERNAME));
        onView(withId(R.id.password)).perform(replaceText(FAKE_PASSWORD_INVALID));

        onView(withId(R.id.loginBttn)).perform(click());

        assertFalse(activityRule.getActivity().loginSuccessful);
    }

    @Test
    public void checkDonator() {
        onView(withId(R.id.username)).perform(replaceText(FAKE_USERNAME));
        onView(withId(R.id.password)).perform(replaceText(FAKE_PASSWORD_VALID));

        onView(withId(R.id.loginBttn)).perform(click());

        assertTrue(activityRule.getActivity().donatorLoginSuccessful);
    }

    @Test
    public void checkRecipient() {
        onView(withId(R.id.username)).perform(replaceText(FAKE_USERNAME));
        onView(withId(R.id.password)).perform(replaceText(FAKE_PASSWORD_VALID));

        onView(withId(R.id.loginBttn)).perform(click());

        assertFalse(activityRule.getActivity().recipientLoginSuccessful);
    }
}

  /*

    @Test
    public void username_EditText_Test() {

        //Test if the usernameID editText widget correctly gets the written username.

        onView(withId(R.id.username))
                .perform(typeText(FAKE_USERNAME), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(FAKE_PASSWORD_VALID), closeSoftKeyboard());
        onView(withId(R.id.loginBttn)).perform(click());


    }

    @Test
    public void password_EditText_Test() {

        //Test if the passwordID editText widget correctly gets written the password.

        myObjectUnderTest.passwordID.setText("");
        myObjectUnderTest.passwordID.append(FAKE_PASSWORD_VALID);
        String testString = myObjectUnderTest.passwordID.getText().toString();
        assertEquals(testString, FAKE_PASSWORD_VALID);

    }

    @Test
    public void checkPasswords_EDITTEXT_Username_and_Password_Valid_Test() {

        //Test if you can log in with the information you entered.

        myObjectUnderTest.usernameID.setText("");
        myObjectUnderTest.usernameID.append(FAKE_USERNAME);
        myObjectUnderTest.passwordID.setText("");
        myObjectUnderTest.passwordID.append(FAKE_PASSWORD_VALID);

        boolean result = myObjectUnderTest.myDB.comparePasswordAndLogin(myObjectUnderTest.usernameID.getText().toString(), myObjectUnderTest.passwordID.getText().toString());

        assertTrue(result);
    }

    @Test
    public void checkPasswords_EDITTEXT_Username_Invalid_Test() {

        //Test if an invalid password you enter is NOT accepted.

        myObjectUnderTest.usernameID.setText("");
        myObjectUnderTest.usernameID.append(FAKE_USERNAME_INVALID);
        myObjectUnderTest.passwordID.setText("");
        myObjectUnderTest.passwordID.append(FAKE_PASSWORD_VALID);

        boolean result = myObjectUnderTest.myDB.comparePasswordAndLogin(myObjectUnderTest.usernameID.getText().toString(), myObjectUnderTest.passwordID.getText().toString());

        assertTrue(result);
    }

    @Test
    public void checkPasswords_EDITTEXT_Password_Invalid_Test() {

        //Test if an invalid password you enter is NOT accepted.

        myObjectUnderTest.usernameID.setText("");
        myObjectUnderTest.usernameID.append(FAKE_USERNAME);
        myObjectUnderTest.passwordID.setText("");
        myObjectUnderTest.passwordID.append(FAKE_PASSWORD_INVALID);

        boolean result = myObjectUnderTest.myDB.comparePasswordAndLogin(myObjectUnderTest.usernameID.getText().toString(), myObjectUnderTest.passwordID.getText().toString());

        assertTrue(result);
    }

    @Test
    public void checkPasswords_Username_and_Password_Valid_Test() {

        //Tests if the database has the valid username and password at all.

        boolean result = myObjectUnderTest.myDB.comparePasswordAndLogin(FAKE_USERNAME, FAKE_PASSWORD_VALID);

        assertTrue(result);
    }

    @Test
    public void checkPasswords_Username_Invalid_Test() {

        //Tests if the database rejects a false username.

        boolean result = myObjectUnderTest.myDB.comparePasswordAndLogin(FAKE_USERNAME_INVALID, FAKE_PASSWORD_VALID);

        assertTrue(result);
    }

    @Test
    public void checkPasswords_Password_Invalid_Test() {

        //Tests if the database rejects a false password.

        boolean result = myObjectUnderTest.myDB.comparePasswordAndLogin(FAKE_USERNAME, FAKE_PASSWORD_INVALID);

        assertTrue(result);
    }

    @After
    public void tearDown() {
        myObjectUnderTest = null;
    }
*/



