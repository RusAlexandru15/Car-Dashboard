package com.example.myapplication1710;

import android.os.Handler;
import android.widget.TextView;



public class VehicleDataTask implements Runnable{

    //pentru Handler ai doua importuri ,doar unu merge
    private Handler handler;

    private int currentRpm;
    private TextView textView;

    private EngineDataStream engineDataStream;

    //constructor
    public VehicleDataTask(Handler handler, EngineDataStream engineDataStream, TextView textView) {
        this.handler = handler;
        this.engineDataStream = engineDataStream;
        this.textView = textView;

        //engine starts at 900rpm
        this.currentRpm=900;
    }

    public void setEngineDataStream(EngineDataStream engineDataStream) {
        this.engineDataStream = engineDataStream;
    }

    //functia care ruleaza pe alt thread
    @Override
    public void run() {
        String rpm =engineDataStream.requestRPM();
        int newRpm= HexConverter.fourHexConvert(rpm)/4;

       // int rpmtoShow=0;
        //if(Math.abs(newRpm-this.currentRpm)>10)


        textView.setText(String.valueOf(newRpm));
        handler.postDelayed(this, 100); // Actualizează TextView-ul la fiecare 1 secundă
    }

}
