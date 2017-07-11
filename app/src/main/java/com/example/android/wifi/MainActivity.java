package com.example.android.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    WifiManager mainWifiObj;
    List<ScanResult> results;
    int size=0 ;
    String ITEM_KEY = "key";
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;
    static final public List<WifiP2pDevice> peers = new ArrayList<>();
  //  private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();


    private WifiP2pManager.PeerListListener peerListListener ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
    }

    public void discover(View v) {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank.  Code for peer discovery goes in the
                // onReceive method, detailed below.

                Toast.makeText(MainActivity.this, "discover sucess", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int reasonCode) {
                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
            }
        });
    }


    public void connect(View c) {
        peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable( final WifiP2pDeviceList peerList) {
                Toast.makeText(MainActivity.this, "conne sucess", Toast.LENGTH_LONG).show();


           //  final  WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    //    ArrayList<WifiP2pDevice> refreshedPeers = (ArrayList<WifiP2pDevice>) peerList.getDeviceList();


            }

        };};


    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
        private WifiP2pManager mManager;
        private WifiP2pManager.Channel mChannel;
        private MainActivity mActivity;

        public WiFiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                           MainActivity activity) {
            super();
            this.mManager = manager;
            this.mChannel = channel;
            this.mActivity = activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
                // Check to see if Wi-Fi is enabled and notify appropriate activity
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    // Wifi P2P is enabled
                } else {
                    // Wi-Fi P2P is not enabled
                }
            } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
                // Call WifiP2pManager.requestPeers() to get a list of current peers

                if (mManager != null) {
                    mManager.requestPeers(mChannel, peerListListener);
                }
                //  Log.d(WiFiDirectActivity.TAG, "P2P peers changed");
            } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
                // Respond to new connection or disconnections
                Log.v("Message","CONNECTION_CHANGED_ACTION");
            } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
                // Respond to this device's wifi state changing
                Log.v("Message","WIFI_P2P_THIS_DEVICE_CHANGED");

            }
            else if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action))
            {


                mainWifiObj = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

                mainWifiObj.startScan ();
             //   mainWifiObj.startScan ();
                mainWifiObj.getWifiState();
             List<ScanResult> results =  mainWifiObj.getScanResults();

                final int N = results.size();
String TAG ="debug";
                Log.v(TAG, "Wi-Fi Scan Results ... Count:" + N);
                for(int i=0; i < N; ++i) {
                    Log.v(TAG, "  BSSID       =" + results.get(i).BSSID);
                    Log.v(TAG, "  SSID        =" + results.get(i).SSID);
                    Log.v(TAG, "  Capabilities=" + results.get(i).capabilities);
                    Log.v(TAG, "  Frequency   =" + results.get(i).frequency);
                    Log.v(TAG, "  Level       =" + results.get(i).level);
                    Log.v(TAG, "---------------");
                }
               // Log.v("Mohm",String.valueOf(wifiScanList.get(1)).toString());
            }
        }

    }


}
