package com.example.myapplication1710;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class BluetoothService {

    //furnizeaza lista de paired devices
    private  BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice elm327Device;
    private BluetoothSocket bluetoothSocket;
    private boolean connectionSuccess;

    //for exchanging data
   // private DataStream dataStream;

    //128-bit  UUID pentru Serial Port Profile (SPP)
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final String elm327Name="OBDII";

    //only for debugging
    private List<String> receivedMessages;
    //retrieving data
    private OutputStream outputStream;
    private InputStream inputStream;




    //constructor
    public BluetoothService() {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //actual bluetooth device
        this.elm327Device=null;
        this.bluetoothSocket=null;

        this.connectionSuccess=false;

        this.receivedMessages=new ArrayList<>();

    }


    //main connection function ... to be called from outside
    @SuppressLint("MissingPermission")
    void serveConnection(){
        //search elm from paired device list
        this.findElm327();

        // Cancel discovery because it otherwise slows down the connection.
        this.bluetoothAdapter.cancelDiscovery();

        //establish a connection chanel between AndroidPhone and elm
        //connectionSuccess keeps the state of the connection : true/false
        this.connectionSuccess= this.connectToELM327Device();

        //doar daca am conexiune trimit request-uri
        if(this.connectionSuccess) {
            this.openStreams();
            this.requestInitCommands();
        }
    }


    @SuppressLint("MissingPermission")
    void findElm327() {
        for (BluetoothDevice device : bluetoothAdapter.getBondedDevices()) {
            if(Objects.equals(device.getName(), this.elm327Name)) {
                this.elm327Device = device;
                break;
            }
        }
    }

    public boolean isConnected() {
        return this.connectionSuccess;
    }



    //*************************** DEBUGGING FUNCTIONS ************************************


    String showConnectionState(){
        if(this.connectionSuccess)
            return "CONNECTED SUCCESSFULLY";
        else
            return "FAILED TO CONNECT";
    }

    //afisez toate mesajele venite de la elm327
    String showReceivedMessages(){
        StringBuilder message= new StringBuilder();
        message.append("bunaziua");
        for (String str : this.receivedMessages)
            message.append("||").append(str);

        //verific numaru de raspunsuri
        message.append("raspunsuri :").append(this.receivedMessages.size());

        return message.toString();
    }

//*************************** END DEBUGGING FUNCTIONS **********************************

    @SuppressLint("MissingPermission")
    private boolean connectToELM327Device() {
        if(this.elm327Device!=null) {
            try {
                // Create a BluetoothSocket and connect to the device
                this.bluetoothSocket = elm327Device.createRfcommSocketToServiceRecord(SPP_UUID);
                this.bluetoothSocket.connect();
                return true;

                // Connection successful
            } catch (IOException e) {
                e.printStackTrace();
                // Handle connection errors
            }
            return false;
        }

        return false;
    }



    // Closes the client socket and causes the thread to finish.
    public void closeConnection() {
        try {
            this.bluetoothSocket.close();
            this.closeStreams();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //********************* open/close streams *********************************

    //functii data stream
    public void openStreams() {
        try {
            this.outputStream = this.bluetoothSocket.getOutputStream();
            this.inputStream = this.bluetoothSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //to be called when closing the connection
    public void closeStreams() {
        if (this.outputStream != null) {
            try {
                this.outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //********************* exchanging data *********************************

    public String sendAndReceive(String command) {
        try {
            this.outputStream.write(command.getBytes());
            byte[] buffer = new byte[1024];
            int bytesRead = this.inputStream.read(buffer);
            return new String(buffer, 0, bytesRead);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }


    void requestInitCommands(){
        this.sendAndReceive("AT D");
        this.sendAndReceive("AT Z");
        this.sendAndReceive("AT SP6");

    }


    String requestRPM(){
        if(this.connectionSuccess)
           return this.sendAndReceive("010C");

        else return "0000";
    }

    String requestSpeed(){
        if(this.connectionSuccess)
            return this.sendAndReceive("010D");
        else return "00";
    }

    String requestRPMSpeed(){
        if(this.connectionSuccess)
            return this.sendAndReceive("010C0D");
        else return "00";
    }

}
