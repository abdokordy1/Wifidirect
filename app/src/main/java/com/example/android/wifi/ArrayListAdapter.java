package com.example.android.wifi;

import android.content.Context;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Abdallah on 5/30/2017.
 */

public class ArrayListAdapter extends ArrayAdapter<WifiP2pDevice> {


        public  ArrayListAdapter(Context context , List<WifiP2pDevice> users)
        {
            super(context,android.R.layout.select_dialog_item,users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_view, parent, false);
            }


          TextView Text1= (TextView) convertView.findViewById(R.id.txt1);
           TextView Text2 =(TextView) convertView.findViewById(R.id.id2);


            WifiP2pDevice device = MainActivity.peers.get(position);

            WifiP2pConfig config = new WifiP2pConfig();
            config.deviceAddress = device.deviceAddress;
            config.wps.setup = WpsInfo.PBC;
           Text1.setText(device.toString());
          Text2.setText(config.deviceAddress.toString());

            return convertView;
        }





}
