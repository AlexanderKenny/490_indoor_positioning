package com.queens490.alexander.indoor_positioning;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class settingFragment extends Fragment {
    private View settingView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        settingView = inflater.inflate(R.layout.settings, container, false);
        return settingView;
    }

/*    public EditText[] getEditTexts(){
        EditText[] ourEditTexts = new EditText[]{settingView.findViewById(R.id.beaconA_x), settingView.findViewById(R.id.beaconA_y),
                settingView.findViewById(R.id.beaconB_x), settingView.findViewById(R.id.beaconB_y),
                settingView.findViewById(R.id.beaconC_x), settingView.findViewById(R.id.beaconC_y),
                settingView.findViewById(R.id.beaconD_x), settingView.findViewById(R.id.beaconD_y)};
        return ourEditTexts;
    }*/

}
