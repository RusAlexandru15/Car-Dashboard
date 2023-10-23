package com.example.myapplication1710;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

//runnable ???
public class EngineDataStream {
    private OutputStream outputStream;
    private InputStream inputStream;

    private boolean connectionSuccess=false;

    public void setConnectionSuccess(boolean connectionSuccess) {
        this.connectionSuccess = connectionSuccess;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

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

    String requestRPM() {
        if(!this.connectionSuccess)
            return "0000";

        String finalRPM="";
        String response = this.sendAndReceive("010C");

        List<String> responseParts = Arrays.asList(response.split(" "));

        if(responseParts.size()>=3)
            finalRPM=responseParts.get(1)+responseParts.get(2);
        //only 4 bytes
        //after that comes the conversion from bytes to int (only on last 2 bytes)
        return  finalRPM; // RISKING INDEX OUT OF BOUNDS +"||"+response.substring(4,7)+"||"+response.length();
    }
}
