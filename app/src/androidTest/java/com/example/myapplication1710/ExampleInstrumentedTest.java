package com.example.myapplication1710;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.myapplication1710", appContext.getPackageName());
    }

    @Test
    public void testHexConversionRPM(){
       assertEquals(HexConverter.fourHexConvert("0D80"),3456);
        assertEquals(HexConverter.fourHexConvert("0000"),0);
        assertEquals(HexConverter.fourHexConvert("0D8090"),-1);

    }

    @Test
    public void testHexConversionSpeed(){
        assertEquals(HexConverter.twoHexConvert("0D80"),-1);
        assertEquals(HexConverter.twoHexConvert("00"),0);
        assertEquals(HexConverter.twoHexConvert("FF"),255);

        assertEquals(HexConverter.twoHexConvert("C1"),193);

    }

}