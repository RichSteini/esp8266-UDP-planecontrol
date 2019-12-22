package com.example.richard.udp_control_station;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;


public class UDPClient extends AsyncTask<String,Void,Boolean>
{
    private InetAddress destinationAddress;
    private int destinationPort;
    private Queue<String> queue;

    private UDPClient()
    {
        super();
        queue = new LinkedList<>();
    }

    public UDPClient(String destinationAddress, int destinastionPort) throws IOException
    {
        super();
        queue = new LinkedList<>();
        this.destinationAddress = InetAddress.getByName(destinationAddress);
        this.destinationPort = destinastionPort;
    }

    public void addElementToQueue(String element)
    {
        queue.add(element);
    }

    protected Boolean doInBackground(String... command)
    {
        byte [] data = command[0].getBytes(StandardCharsets.US_ASCII);
        byte [] incomingData = new byte[1];
        try
        {
            boolean keepSending = true;
            int counter = 0;

            DatagramSocket udpSocket = new DatagramSocket(7070);
            udpSocket.setSoTimeout(500);
            DatagramPacket packet = new DatagramPacket(data,data.length,this.destinationAddress,destinationPort);
            DatagramPacket incpacket = new DatagramPacket(incomingData,incomingData.length);

            while (keepSending && counter < 20)
            {
                counter++;
                udpSocket.send(packet);
                try
                {
                    udpSocket.receive(incpacket);
                    String receivedString = new String(incpacket.getData(),0,incpacket.getLength());

                    if(receivedString.equals("F"))
                    {
                        counter = 0;
                    }
                    else if (receivedString.equals("T"))
                    {
                        keepSending = false;
                    }
                }
                catch (SocketTimeoutException e)
                {

                }
            }
            udpSocket.close();

        }
        catch (IOException e)
        {
            Log.e("IO error: ", e.getMessage());
        }

        String nextCommand = queue.poll();

        if (nextCommand != null)
        {
            doInBackground(nextCommand);
        }

        return null;
    }
}
