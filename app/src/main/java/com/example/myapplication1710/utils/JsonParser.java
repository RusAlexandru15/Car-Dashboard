package com.example.myapplication1710.utils;

import android.content.res.AssetManager;

import com.example.myapplication1710.electronic_horizon.Road;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class JsonParser {
    public static Road readJSONFile(AssetManager assetManager, String fileName) {
        Road road = null;

        try {
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            Gson gson = new Gson();
            road = gson.fromJson(stringBuilder.toString(), Road.class);

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return road;
    }






}
