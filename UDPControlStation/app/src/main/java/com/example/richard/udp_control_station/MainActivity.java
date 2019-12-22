package com.example.richard.udp_control_station;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends Activity
{
    private TextView textView;
    private SeekBar seekBar;

    private UDPClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize all variables
        initializeVariables();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int progressval = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                progressval = progress;
                textView.setText("Covered: " + progressval + "/" + seekBar.getMax());
                String senddata = "l"+ progressval;
                handleAsyncTasks(senddata);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }

    // A private method to help us initialize our variables.
    private void initializeVariables()
    {
        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar2);
    }

    private void handleAsyncTasks(String command)
    {
        if(client == null)
        {
            createUDPClient();
        }

        if(client.getStatus() == AsyncTask.Status.FINISHED || client.getStatus() == AsyncTask.Status.PENDING)
        {
            createUDPClient();
            client.execute(command);
        }
        else if (client.getStatus() == AsyncTask.Status.RUNNING)
        {
            client.addElementToQueue(command);
        }

    }

    private void createUDPClient()
    {
        try
        {
            client = new UDPClient("192.168.4.1",9090);
        }
        catch (IOException e)
        {
            Log.e("UDP connect error: ", e.getMessage());
        }
    }
}
