package com.example.myapplication1710;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


//tutorial
//https://www.youtube.com/watch?v=fis26HvvDII&t=2917s&ab_channel=freeCodeCamp.org

//asta ii de 4  orel
//https://www.youtube.com/watch?v=tZvjSl9dswg&t=2s&ab_channel=CalebCurry

/** START POINT APP   one Activity<----> one Page */
public class MainActivity extends AppCompatActivity {

//campurile clasei MainActivity
    private TextView txtHello;
    private Handler handler;

    private VehicleDataTask vehicleDataTask;
    private BluetoothService bluetoothService;
    private  EngineDataStream engineDataStream;



    /** START POINT APP */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //legatura dintre java class si layout-ul .xml
        setContentView(R.layout.activity_main);


        //partea de AUtoincrement
        this.txtHello = this.findViewById(R.id.textView1);
        this.handler = new Handler();

        this.engineDataStream=new EngineDataStream();

        this.vehicleDataTask = new VehicleDataTask(this.handler, this.engineDataStream, this.txtHello);

    }



    /**event handler
      listener pentru BUTON !!! declarata in atributele butonului
      View .. v e INSTANTA BUTONULUI
     POTI din View sa faci CAST la Button si ai alte functii de button*/
    @SuppressLint("SetTextI18n")
    public void onBtnClick(View v){

        int buttonId = v.getId();

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Paired Devices RPM")
                //.setMessage(this.bluetoothService.requestRPM())
                .setMessage(this.engineDataStream.requestRPM())
                .setPositiveButton("OK", null)
                .show();

    }

    // LISTENER butonul de conexiune cu device-ul
    public void connectToDevice(View view) {

        Button button = (Button) view;


        //initializare bluetooth service
        this.bluetoothService=new BluetoothService();
        //start the connection
        this.bluetoothService.serveConnection();

        //send initialization commands
        this.bluetoothService.requestInitCommands();

        if(this.bluetoothService.isConnected()) {

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Paired Devices RPM")
                    .setMessage("CONNECTED SUCCESSFULLY")
                    .setPositiveButton("OK", null)
                    .show();
            button.setEnabled(false);
            //set fields for engineDataStream
            this.engineDataStream.setInputStream(this.bluetoothService.getInputStream());
            this.engineDataStream.setOutputStream(this.bluetoothService.getOutputStream());
            this.engineDataStream.setConnectionSuccess(true);

            //setez la vehicleDataTask engineStream-ul
            this.vehicleDataTask.setEngineDataStream(this.engineDataStream);
        }
        else {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Paired Devices RPM")
                    .setMessage("UNABLE TO CONNECT")
                    .setPositiveButton("OK", null)
                    .show();
        }

    }





    /** ANDROID ACTIVITY LIFE CYCLE */

    //NU NUMARA FARA ASTA
    // Pornirea incrementării automate când activitatea(pagina) este afișată
    @Override
    protected void onResume() {
        super.onResume();

        //partea de post trebuie sa fie un RUNNABLE , in cazul nostru autoIncrement
        //post porneste executia thread-ului
        this.handler.post(vehicleDataTask);
    }




    //la inchiderea activitatii
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(vehicleDataTask); // Stop the auto-increment thread

        //disconnect bluetooth device
        this.bluetoothService.closeConnection();

    }


}