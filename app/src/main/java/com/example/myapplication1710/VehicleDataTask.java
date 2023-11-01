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
    private int currentSpeed;

    private TextView textRPM;
    private TextView textSpeed;
    private BluetoothService bluetoothService;




    //constructor

    public VehicleDataTask(Handler handler, TextView textRPM, TextView textSpeed) {
        this.handler = handler;

        this.textRPM = textRPM;

        this.textSpeed=textSpeed;

        //initializez cu null
        this.bluetoothService = null;


        
        //engine starts at 900rpm with 0km/h
        this.currentRpm = 900;
        this.currentSpeed=0;
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
            this.textRPM.setText(String.valueOf(this.currentRpm));
            this.textSpeed.setText(String.valueOf(this.currentSpeed));
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

            textRPM.setText(String.valueOf(realrpmValue));
            handler.postDelayed(this, 500);
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
