package com.example.myapplication1710;


import static java.lang.Math.abs;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import de.nitri.gauge.Gauge;


public class VehicleDataTask implements Runnable {

    //pentru Handler ai doua importuri ,doar unu merge
    private Handler handler;
    private int currentRpm;
    private int currentSpeed;
    private float rpmToShow;


    private TextView textRPM;
    private TextView textSpeed;
    private Gauge gaugeRpm;
    private Gauge gaugeSpeed;
    private BluetoothService bluetoothService;


    //constructor

    public VehicleDataTask(Handler handler, TextView textRPM, TextView textSpeed ,Gauge gaugeRpm, Gauge gaugeSpeed) {
        this.handler = handler;

        //elemente grafice
        this.textRPM = textRPM;

        this.textSpeed = textSpeed;

        this.gaugeRpm =gaugeRpm;
        this.gaugeSpeed=gaugeSpeed;

        //initializez cu null
        this.bluetoothService = null;


        //engine starts at 900rpm with 0km/h
        this.currentRpm = 900;
        this.currentSpeed = 0;
        this.rpmToShow=900;


        //testing gauge
    }


    public void setBluetoothService(BluetoothService bs) {
        this.bluetoothService = bs;
    }



    //functia care ruleaza pe alt thread trebuie modificata logica
    //trebe sa adaugi logica
    @SuppressLint("SetTextI18n")
    @Override
    public void run() {

        if (this.bluetoothService == null) {
            this.textRPM.setText(String.valueOf(this.currentRpm));
            this.textSpeed.setText(String.valueOf(this.currentSpeed));

            this.gaugeRpm.moveToValue((float)this.currentRpm/100);
            this.gaugeSpeed.moveToValue(this.currentSpeed);

            //for testing

           // this.currentRpm+=150;
            //if(this.currentRpm>=7000)
              //  this.currentRpm=0;

            //end for testing

            this.handler.postDelayed(this, 500);
            //return
        } else {

            //######################### rpm & speed request #################
            String rpmSpeed = "";
            String realRPMSpeed= "";



             rpmSpeed = bluetoothService.requestRPMSpeed();

             realRPMSpeed =extractRealRpmSpeed(rpmSpeed);


             if(realRPMSpeed.length() >= 5) {
                String realRPM = realRPMSpeed.substring(0, 4);
                String realSpeed =realRPMSpeed.substring(4,6);

                this.currentRpm = HexConverter.fourHexConvert(realRPM) / 4;
                this.currentSpeed =HexConverter.twoHexConvert(realSpeed);
             }


            //rpm-ul minim e de 900 pentru motor pornit
            if (this.currentRpm < 900)
                this.currentRpm = 900;

            //rotunjire la cel mai apropiat multiplu de 10
            if(abs(this.currentRpm-this.rpmToShow) >10 )
                this.rpmToShow=this.currentRpm - (this.currentRpm % 10);



            this.gaugeRpm.moveToValue( this.rpmToShow/100);
            this.gaugeSpeed.moveToValue(this.currentSpeed);

            this.textRPM.setText(String.valueOf(this.currentRpm));
            this.textSpeed.setText(String.valueOf(this.currentSpeed));

            //am modificat la 250
            this.handler.postDelayed(this, 400);
        }
    }


    /**
     * valoarea de rpm parsata din raspuns
     */
    static String extractRealRpm(String response) {

        String finalRPM = "";

        List<String> responseParts = Arrays.asList(response.split(" "));

        if (responseParts.size() >= 4)
            finalRPM = responseParts.get(2) + responseParts.get(3);

        return finalRPM;
    }

    /** raspuns de forma 0C0D00 primele 4 caractere reprezinta rpm ,ultimele doua viteza*/
    static String extractRealRpmSpeed(String response) {

        String finalRPMSpeed = "";

        List<String> responseParts = Arrays.asList(response.split(" "));

        if (responseParts.size() >= 6)
            finalRPMSpeed = responseParts.get(2) + responseParts.get(3) +responseParts.get(5);

        return finalRPMSpeed;
    }

}
