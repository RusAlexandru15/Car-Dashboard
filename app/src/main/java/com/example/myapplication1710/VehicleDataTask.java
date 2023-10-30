package com.example.myapplication1710;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;


public class VehicleDataTask implements Runnable {

    //pentru Handler ai doua importuri ,doar unu merge
    private Handler handler;
    private int currentRpm;
    private TextView textView;
    private BluetoothService bluetoothService;


    //constructor
    public VehicleDataTask(Handler handler, TextView textView) {
        this.handler = handler;

        this.textView = textView;

        //initializez cu null
        this.bluetoothService = null;

        //engine starts at 900rpm
        this.currentRpm = 900;
    }


    public void setBluetoothService(BluetoothService bs) {
        this.bluetoothService = bs;
    }



    //functia care ruleaza pe alt thread trebuie modificata logica
    //trebe sa adaugi logica
    @SuppressLint("SetTextI18n")
    @Override
    public void run() {

        if (this.bluetoothService == null){
            textView.setText(String.valueOf(this.currentRpm));
            handler.postDelayed(this, 500);
            //return
        }

        else {
            String rpm = this.bluetoothService.requestRPM();


            String realrpm = extractRealRpm(rpm);


            int realrpmValue = HexConverter.fourHexConvert(realrpm) / 4;

            //870 de exp se rotunjeste la 900
            if(realrpmValue<900)
                realrpmValue=900;

            //afisez  prima data realrpm dupa, rpmValue
            textView.setText(String.valueOf(realrpmValue));
            handler.postDelayed(this, 500); // Actualizează TextView-ul la fiecare jumate de  secundă
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

}
