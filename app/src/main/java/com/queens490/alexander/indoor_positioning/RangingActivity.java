package com.queens490.alexander.indoor_positioning;

import android.app.Activity;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.altbeacon.beacon.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class RangingActivity extends Activity implements BeaconConsumer, RangeNotifier {
    protected static final String TAG = "RangingActivity";
    private BeaconManager mBeaconManager;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranging);
        t = (TextView)findViewById(R.id.rangetext);

        mBeaconManager = BeaconManager.getInstanceForApplication(this);

        // In this example, we will use Eddystone protocol, so we have to define it here
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));


        // Binds this activity to the BeaconService
        mBeaconManager.bind(this);

    }

    @Override
    public void onBeaconServiceConnect() {
        // Encapsulates a beacon identifier of arbitrary byte length
        ArrayList<Identifier> identifiers = new ArrayList<>();

        // Set null to indicate that we want to match beacons with any value
        identifiers.add(null);

        // Represents a criteria of fields used to match beacon
        Region region = new Region("AllBeaconsRegion", identifiers);

        try {
            // Tells the BeaconService to start looking for beacons that match the passed Region object
            mBeaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        // Specifies a class that should be called each time the BeaconService gets ranging data, once per second by default
        mBeaconManager.addRangeNotifier(this);
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String debugText = "Beacons in Range: " + beacons.size() + "\n";
                Beacon currentBeacon;
                Iterator<Beacon> beaconIterator = beacons.iterator();
                int x = 0;
                while (x < beacons.size()) {
                    currentBeacon = beaconIterator.next();
                    debugText = debugText + "Address: " + currentBeacon.getBluetoothAddress() + "\n";
                    debugText = debugText + "UUID: " + currentBeacon.getServiceUuid() + "\n";
                    debugText = debugText + "Manufacturer: " + currentBeacon.getManufacturer() + "\n";
                    debugText = debugText + "TX Power: " + currentBeacon.getTxPower() + "\n";
                    debugText = debugText + "RSSI: " + currentBeacon.getRssi() + "\n";
                    debugText = debugText + "AVG RSSI: " + currentBeacon.getRunningAverageRssi() + "\n";
                    debugText = debugText + "Distance: " + currentBeacon.getDistance() + "\n";
                    debugText = debugText + "\n";
                    x++;
                }
                t.setText(debugText);
            }
        });
    }
}