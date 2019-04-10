package com.example.testapp;

import com.example.testapp.ui.SetAddressView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SetAddressViewTest {
    @Rule
    public ActivityTestRule<SetAddressView> activityRule =
            new ActivityTestRule(SetAddressView.class);

    @Test
    public void validatePostalCodeTests1() {
        runTest("12345", 1);
    }

    @Test
    public void validatePostalCodeTests2() {
        runTest("1234567", 1);
    }

    @Test
    public void validatePostalCodeTests3() {
        runTest("ABCDEF",1);
    }

    @Test
    public void validatePostalCodeTests4() {
        runTest("111111",2);
    }

    @Test
    public void validatePostalCodeTests5() {
        runTest("416729",3);
    }


    private void runTest(String input, int expected) {
        SetAddressView setAddressView = activityRule.getActivity();
        int output = setAddressView.validatePostalCode(input);
        assertEquals(expected,output);
    }

}