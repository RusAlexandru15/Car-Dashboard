package com.example.myapplication1710;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication1710.electronic_horizon.Road;
import com.example.myapplication1710.service.BluetoothService;
import com.example.myapplication1710.service.GPSService;
import com.example.myapplication1710.utils.JsonParser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import de.nitri.gauge.Gauge;



public class MainActivity extends AppCompatActivity {

    //elemente grafice
    private TextView txtRPM;
    private TextView txtSpeed;
    private Gauge gaugeRpm;
    private Gauge gaugeSpeed;


    private Handler handler;
    private VehicleDataTask vehicleDataTask;
    private BluetoothService bluetoothService;

    //gps
    private FusedLocationProviderClient fusedLocationClient;
    private TextView latView;
    private TextView longView;
    private GPSService gpsService;
    private TextView segIdView;



    //Electronic Horizon
    private Road road;

    //SL image
    private ImageView slView;



    /** START POINT APP */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //legatura dintre java class si layout-ul .xml
        setContentView(R.layout.activity_main);


        //binding intre campuri si textView-urile din pagina
        this.txtRPM = this.findViewById(R.id.textView1);
        this.txtSpeed = this.findViewById(R.id.textView2);
        this.gaugeRpm=(Gauge)this.findViewById(R.id.gaugerpm);
        this.gaugeSpeed=(Gauge)this.findViewById(R.id.gaugespeed);

        this.handler = new Handler();

        this.vehicleDataTask = new VehicleDataTask(this.handler, this.txtRPM,this.txtSpeed,this.gaugeRpm,this.gaugeSpeed);

        //init SL_View
        this.slView =findViewById(R.id.sl_imageView);

        //gps
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        this.latView = findViewById(R.id.latitude);
        this.longView = findViewById(R.id.longitude);

        this.segIdView=findViewById(R.id.seg_id);

        this.gpsService =new GPSService(fusedLocationClient,slView);
        this.gpsService.setLongView(longView);
        this.gpsService.setLatView(latView);
        //in loc de segView urmeaza imageView!!
        this.gpsService.setSegView(segIdView);
        //this.gpsService.setSlView(slView); mutat in constructor


        //initializare drum
        AssetManager assetManager = getAssets();
        road = JsonParser.readJSONFile(assetManager, "E576.json");
        this.gpsService.setCurrentRoad(road); //setez drumul lui gps service
    }








    // LISTENER butonul de conexiune cu device-ul
    public void connectToDevice(View view) {

        Button button = (Button) view;


        //initializare bluetooth service
        this.bluetoothService=new BluetoothService();
        //start the connection
        this.bluetoothService.serveConnection();

        //send initialization commands ATZ& ATSP6
        this.bluetoothService.requestInitCommands();

        if(this.bluetoothService.isConnected()) {

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Paired Devices RPM")
                    .setMessage("CONNECTED SUCCESSFULLY")
                    .setPositiveButton("OK", null)
                    .show();
            button.setEnabled(false);


            //setez la vehicleDataTask bluetoothService
            this.vehicleDataTask.setBluetoothService(this.bluetoothService);
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
    @Override
    protected void onResume() {
        super.onResume();
        this.handler.post(vehicleDataTask);
    }




    //la inchiderea activitatii
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(vehicleDataTask);
        //disconnect bluetooth device
        this.bluetoothService.closeConnection();

    }


}