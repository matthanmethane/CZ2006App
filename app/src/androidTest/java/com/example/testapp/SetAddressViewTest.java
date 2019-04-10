package com.example.testapp;

import com.example.testapp.ui.SetAddressView;
import com.example.testapp.viewmodel.AddressViewModel;

import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;

public class SetAddressViewTest {
    private SetAddressView setAddressView = new SetAddressView();
    private AddressViewModel model = Mockito.mock(AddressViewModel.class);

    @Test
    public void validatePostalCode1() {
        runTest("12345", 1);
        runTest("1234567", 1);
        runTest("ABCDEF",1);
        runTest("111111",2);
        runTest("416729",3);
    }

    private void runTest(String input, int expected) {
        System.out.println("Test: postal code is '" + input + "'");
        int output = setAddressView.validatePostalCode(input);
        try {
            when(model.execute().get()).thenReturn(null);
            System.out.println(verify(model.execute().get()));
        } catch (Exception e) {
            System.out.println("HI");
        }
        when(model.getValid()).thenReturn(runMockModel(input));
        assertEquals(expected,output);
        System.out.println("Test: passed");
    }

    private boolean runMockModel(String postalCode) {
        AddressViewModel m = new AddressViewModel(postalCode);
        try {
            m.execute().get();
        } catch (Exception e) {
            System.out.println("exception:" + e);
        }
        return m.getValid();
    }
}