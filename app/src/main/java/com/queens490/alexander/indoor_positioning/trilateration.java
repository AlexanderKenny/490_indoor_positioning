package com.queens490.alexander.indoor_positioning;

public class trilateration {

    public static float[] calcTrilateration(float beaconOneDistance, float beaconTwoDistance, float beaconThreeDistance, float beaconOneX, float beaconOneY, float beaconTwoX,float beaconTwoY, float beaconThreeX, float beaconThreeY) {

        // trilateration

        // known beacon locations (fixed on some grid)  ,, make global?
        // each beacon will be given a fixed x,y position on the grid

        // calculated distance from beacons(radii)

        float r1 = beaconOneDistance;
        float r2 = beaconTwoDistance;
        float r3 = beaconThreeDistance;



        // A-F equation to calc system of equations

        float A = -2*beaconOneX + 2*beaconTwoX;
        float B = -2*beaconOneY + 2*beaconTwoY;
        float C = (float) (Math.pow(r1, 2) - Math.pow(r2, 2) - Math.pow(beaconOneX, 2) + Math.pow(beaconTwoX,2) - Math.pow(beaconOneY,2) + Math.pow(beaconTwoY,2));
        float D = -2*beaconTwoX + 2*beaconThreeX;
        float E = -2*beaconTwoY + 2*beaconThreeY;
        float F = (float) (Math.pow(r2, 2) - Math.pow(r3, 2) - Math.pow(beaconTwoX, 2) + Math.pow(beaconThreeX,2) - Math.pow(beaconTwoY,2) + Math.pow(beaconThreeY,2));


        // calculate unknown x,y grid position
        // might need to round the calculated values or add some sort of threshold to display

        float X = (C*E - F*B)/(E*A - B*D);
        float Y = (C*D - A*F)/(B*D - A*E);

        float[] XY = {X,Y};

        return XY;

    }

}
