package com.queens490.alexander.indoor_positioning;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class rangingFragment extends Fragment {

    private Runnable mTicker;
    private Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.debug_ranging_fragment, container, false);
        mHandler = new Handler();
        mTicker = new Runnable() {
            public void run() {
                long now = SystemClock.uptimeMillis();
                long next = now + (1000 - now % 1000);
                mHandler.postAtTime(mTicker, next);
                RangingActivity myRangingActivity = (RangingActivity) getActivity();
                ArrayList<String[]> localBeaconCache = myRangingActivity.getBeaconCache();

                String debugText;
                if (localBeaconCache.isEmpty()) {
                    debugText = "No beacons cached yet";
                }
                else {
                    debugText = "Number of cached Beacons: " + localBeaconCache.size() + "\n";
                    Iterator<String[]> beaconCacheIterator = localBeaconCache.iterator();
                    while (beaconCacheIterator.hasNext()) {
                        String[] currentBeacon = beaconCacheIterator.next();
                        debugText = debugText + "Address: " + currentBeacon[0] + "\n";
                        debugText = debugText + "UUID: " + currentBeacon[1] + "\n";
                        debugText = debugText + "Manufacturer: " + currentBeacon[2] + "\n";
                        debugText = debugText + "TX Power: " + currentBeacon[3] + "\n";
                        debugText = debugText + "RSSI: " + currentBeacon[4] + "\n";
                        debugText = debugText + "AVG RSSI: " + currentBeacon[5] + "\n";
                        debugText = debugText + "Distance: " + currentBeacon[6] + "\n";
                        debugText = debugText + "\n";
                    }
                }
                TextView debugTextView = (TextView)view.findViewById(R.id.rangetext);
                debugTextView.setText(debugText);
            }
        };
        mTicker.run();

        return view;
    }



}
