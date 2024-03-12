package com.example.myapplication1710;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myapplication1710.utils.HexConverter;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RpmDataTest {
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

    @Test
    public void testRpmExtraction(){

        String response1="010C41 0C 0D 54 41 OC OD";
        String response2="010C41 1C 3F ";
        String response3="NO DATA";

        assertEquals(VehicleDataTask.extractRealRpm(response1),"0D54");
        assertEquals(VehicleDataTask.extractRealRpm(response2),"");
        assertEquals(VehicleDataTask.extractRealRpm(response3),"");

    }




}
