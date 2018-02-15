package com.queens490.alexander.indoor_positioning;

import android.app.Activity;
import android.os.RemoteException;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.altbeacon.beacon.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


public class RangingActivity extends AppCompatActivity implements BeaconConsumer, RangeNotifier {
    protected static final String TAG = "RangingActivity";
    private BeaconManager mBeaconManager;
    TextView t;
    private ArrayList<String[]> beaconCache = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranging);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new rangingFragmentPageAdapter(getSupportFragmentManager(),
                RangingActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

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

                Beacon currentBeacon;
                Iterator<Beacon> beaconIterator = beacons.iterator();
                int x = 0;
                while (x < beacons.size()) {
                    currentBeacon = beaconIterator.next();
                    int indexFound=0;
                    if (beaconCache.isEmpty()){
                        beaconCache.add(new String[]{currentBeacon.getBluetoothAddress(),
                                String.valueOf(currentBeacon.getServiceUuid()),
                                String.valueOf(currentBeacon.getManufacturer()),
                                String.valueOf(currentBeacon.getTxPower()),
                                String.valueOf(currentBeacon.getRssi()),
                                String.valueOf(currentBeacon.getRunningAverageRssi()),
                                String.valueOf(currentBeacon.getDistance())
                        });
                    }
                    else{
                        int y=0;
                        boolean beaconCached = false;
                        Iterator<String[]> beaconCacheIterator = beaconCache.iterator();
                        while (beaconCacheIterator.hasNext()){
                            if (beaconCacheIterator.next()[0].equals(currentBeacon.getBluetoothAddress())){
                                //we have cached before store this index to replace
                                beaconCached = true;
                                indexFound=y;
                            }
                            y++;
                        }
                        if (!beaconCached){
                            beaconCache.add(new String[]{currentBeacon.getBluetoothAddress(),
                                    String.valueOf(currentBeacon.getServiceUuid()),
                                    String.valueOf(currentBeacon.getManufacturer()),
                                    String.valueOf(currentBeacon.getTxPower()),
                                    String.valueOf(currentBeacon.getRssi()),
                                    String.valueOf(currentBeacon.getRunningAverageRssi()),
                                    String.valueOf(currentBeacon.getDistance())
                            });
                        }
                        else
                        {
                            beaconCache.set(indexFound,new String[]{currentBeacon.getBluetoothAddress(),
                                    String.valueOf(currentBeacon.getServiceUuid()),
                                    String.valueOf(currentBeacon.getManufacturer()),
                                    String.valueOf(currentBeacon.getTxPower()),
                                    String.valueOf(currentBeacon.getRssi()),
                                    String.valueOf(currentBeacon.getRunningAverageRssi()),
                                    String.valueOf(currentBeacon.getDistance())
                            });
                        }
                    }
                    x++;
                }

    }
    public ArrayList<String[]> getBeaconCache(){
        return beaconCache;
    }
}