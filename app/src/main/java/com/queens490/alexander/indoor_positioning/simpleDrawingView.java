package com.queens490.alexander.indoor_positioning;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static com.queens490.alexander.indoor_positioning.RangingActivity.TAG;

public class simpleDrawingView extends View {
    private final int beaconPaintColour = Color.RED;
    private final int locationPaintColour = Color.GREEN;
    private final int rangePaintColour = Color.BLUE;

    private Paint beaconPaint;
    private Paint locationPaint;
    private Paint rangePaint;
    private Paint textPaint;
    private Paint averagePaint;
    double beaconA_x, beaconA_y, beaconB_x, beaconB_y, beaconC_x, beaconC_y, beaconD_x, beaconD_y, x_dims, y_dims, viewWidth, viewHeight;
    double x_sf, y_sf;
    double rangeA, rangeB, rangeC, rangeD;
    ArrayList<Double> xValues = new ArrayList<Double>();
    ArrayList<Double> yValues = new ArrayList<Double>();
    EditText beaconA_x_text, beaconA_y_text, beaconB_x_text, beaconB_y_text, beaconC_x_text, beaconC_y_text, beaconD_x_text, beaconD_y_text;

    private Runnable mTicker;
    private Handler mHandler;

    public simpleDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        mHandler = new Handler();
        mTicker = new Runnable() {
            public void run() {
                long now = SystemClock.uptimeMillis();
                long next = now + (1000 - now % 1000);
                mHandler.postAtTime(mTicker, next);
                postInvalidate();
            }
        };
        mTicker.run();
        setupPaint();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {

        RangingActivity myRangingActivity = (RangingActivity) this.getContext();
        //settingFragment mySettingFragment = new settingFragment();

        ArrayList<String[]> localBeaconCache = myRangingActivity.getBeaconCache();

        /*EditText[] localEditTexts = mySettingFragment.getEditTexts();
        beaconA_x_text = localEditTexts[0];
        beaconA_y_text = localEditTexts[1];
        beaconB_x_text = localEditTexts[2];
        beaconB_y_text = localEditTexts[3];
        beaconC_x_text = localEditTexts[4];
        beaconC_y_text = localEditTexts[5];
        beaconD_x_text = localEditTexts[6];
        beaconD_y_text = localEditTexts[7];

        Log.d(TAG, "beaconA_x_text: " + beaconA_x_text);
        Log.d(TAG, "beaconA_y_text: " + beaconA_y_text);
        Log.d(TAG, "beaconB_x_text: " + beaconB_x_text);
        Log.d(TAG, "beaconB_y_text: " + beaconB_y_text);
        Log.d(TAG, "beaconC_x_text: " + beaconC_x_text);
        Log.d(TAG, "beaconC_y_text: " + beaconC_y_text);
        Log.d(TAG, "beaconD_x_text: " + beaconD_x_text);
        Log.d(TAG, "beaconD_y_text: " + beaconD_y_text);

        if (beaconA_x_text == null || beaconA_y_text == null
                || beaconB_x_text == null || beaconB_y_text == null
                || beaconC_x_text == null || beaconC_y_text == null
                || beaconD_x_text == null || beaconD_y_text == null)
        {*/
            beaconA_x = 3.57;
            beaconA_y = 0;
            beaconB_x = 0;
            beaconB_y = 0;
            beaconC_x = 3.57;
            beaconC_y = 4.21;
            beaconD_x = 0;
            beaconD_y = 4.21;
        /*}
        else {
            beaconA_x = Double.valueOf(beaconA_x_text.getText().toString());
            beaconA_y = Double.valueOf(beaconA_y_text.getText().toString());
            beaconB_x = Double.valueOf(beaconB_x_text.getText().toString());
            beaconB_y = Double.valueOf(beaconB_y_text.getText().toString());
            beaconC_x = Double.valueOf(beaconC_x_text.getText().toString());
            beaconC_y = Double.valueOf(beaconC_y_text.getText().toString());
            beaconD_x = Double.valueOf(beaconD_x_text.getText().toString());
            beaconD_y = Double.valueOf(beaconD_y_text.getText().toString());
        }

        Log.d(TAG, "beaconA_x: " + beaconA_x);
        Log.d(TAG, "beaconA_y: " + beaconA_y);
        Log.d(TAG, "beaconB_x: " + beaconB_x);
        Log.d(TAG, "beaconB_y: " + beaconB_y);
        Log.d(TAG, "beaconC_x: " + beaconC_x);
        Log.d(TAG, "beaconC_y: " + beaconC_y);
        Log.d(TAG, "beaconD_x: " + beaconD_x);
        Log.d(TAG, "beaconD_y: " + beaconD_y);*/

        xValues.add(beaconA_x);
        xValues.add(beaconB_x);
        xValues.add(beaconC_x);
        xValues.add(beaconD_x);
        yValues.add(beaconA_y);
        yValues.add(beaconB_y);
        yValues.add(beaconC_y);
        yValues.add(beaconD_y);

        viewHeight=this.getHeight();
        viewWidth=this.getWidth();


        x_dims = Collections.max(xValues);
        y_dims = Collections.max(yValues);

        x_sf = viewWidth / x_dims;
        y_sf = viewHeight / y_dims;

        canvas.drawCircle((float)(beaconA_x*x_sf), (float)(beaconA_y*y_sf), 50, beaconPaint);
        canvas.drawCircle((float)(beaconB_x*x_sf), (float)(beaconB_y*y_sf), 50, beaconPaint);
        canvas.drawCircle((float)(beaconC_x*x_sf), (float)(beaconC_y*y_sf), 50, beaconPaint);
        canvas.drawCircle((float)(beaconD_x*x_sf), (float)(beaconD_y*y_sf), 50, beaconPaint);

        Iterator<String[]> beaconCacheIterator = localBeaconCache.iterator();
        while (beaconCacheIterator.hasNext()) {
            String[] currentBeacon = beaconCacheIterator.next();
            if (currentBeacon[0].equals("FE:DB:9F:5F:A0:BF"))
            {
                //Beacon A
                rangeA = Double.parseDouble(currentBeacon[6]);
                canvas.drawOval((float)((beaconA_x*x_sf)-(rangeA*x_sf)),(float)((beaconA_y*y_sf)-(rangeA*y_sf)),(float)((beaconA_x*x_sf)+(rangeA*x_sf)),(float)((beaconA_y*y_sf)+(rangeA*y_sf)),rangePaint);
                canvas.drawText(String.format("Beacon A: %.2f", rangeA),(float)(beaconA_x*x_sf)-400,(float)(beaconA_y*y_sf)+80 ,textPaint);
            }
            else if (currentBeacon[0].equals("C9:92:A7:06:56:12"))
            {
                //Beacon B
                rangeB = Double.parseDouble(currentBeacon[6]);
                canvas.drawOval((float)((beaconB_x*x_sf)-(rangeB*x_sf)),(float)((beaconB_y*y_sf)-(rangeB*y_sf)),(float)((beaconB_x*x_sf)+(rangeB*x_sf)),(float)((beaconB_y*y_sf)+(rangeB*y_sf)),rangePaint);
                canvas.drawText(String.format("Beacon B: %.2f", rangeB),(float)(beaconB_x*x_sf)+20,(float)(beaconB_y*y_sf)+80 ,textPaint);
            }
            else if (currentBeacon[0].equals("E1:08:67:65:8E:77"))
            {
                //Beacon C
                rangeC = Double.parseDouble(currentBeacon[6]);
                canvas.drawOval((float)((beaconC_x*x_sf)-(rangeC*x_sf)),(float)((beaconC_y*y_sf)-(rangeC*y_sf)),(float)((beaconC_x*x_sf)+(rangeC*x_sf)),(float)((beaconC_y*y_sf)+(rangeC*y_sf)),rangePaint);
                canvas.drawText(String.format("Beacon C: %.2f", rangeC),(float)(beaconC_x*x_sf)-400,(float)(beaconC_y*y_sf)-20 ,textPaint);
            }
            else if (currentBeacon[0].equals("CA:D9:34:54:E9:71"))
            {
                //Beacon D
                rangeD = Double.parseDouble(currentBeacon[6]);
                canvas.drawOval((float)((beaconD_x*x_sf)-(rangeD*x_sf)),(float)((beaconD_y*y_sf)-(rangeD*y_sf)),(float)((beaconD_x*x_sf)+(rangeD*x_sf)),(float)((beaconD_y*y_sf)+(rangeD*y_sf)),rangePaint);
                canvas.drawText(String.format("Beacon D: %.2f",rangeD),(float)(beaconD_x*x_sf)+20,(float)(beaconD_y*y_sf)-20 ,textPaint);
            }
        }
        trilateration trilaterationCalc = new trilateration();
        float[] trilaterationPos = trilaterationCalc.calcTrilateration((float)rangeA, (float)rangeB, (float)rangeD, (float)beaconA_x, (float)beaconA_y, (float)beaconB_x, (float)beaconB_y, (float)beaconD_x, (float)beaconD_y);
        float xAvg = trilaterationPos[0];
        float yAvg = trilaterationPos[1];
        float[] trilaterationPos2 = trilaterationCalc.calcTrilateration((float)rangeD, (float)rangeB, (float)rangeC, (float)beaconD_x, (float)beaconD_y, (float)beaconB_x, (float)beaconB_y, (float)beaconC_x, (float)beaconC_y);
        xAvg += trilaterationPos2[0];
        yAvg += trilaterationPos2[1];
        float[] trilaterationPos3 = trilaterationCalc.calcTrilateration((float)rangeA, (float)rangeD, (float)rangeC, (float)beaconA_x, (float)beaconA_y, (float)beaconD_x, (float)beaconD_y, (float)beaconC_x, (float)beaconC_y);
        xAvg += trilaterationPos3[0];
        yAvg += trilaterationPos3[1];
        float[] trilaterationPos4 = trilaterationCalc.calcTrilateration((float)rangeA, (float)rangeB, (float)rangeC, (float)beaconA_x, (float)beaconA_y, (float)beaconB_x, (float)beaconB_y, (float)beaconC_x, (float)beaconC_y);
        xAvg += trilaterationPos4[0];
        yAvg += trilaterationPos4[1];
        xAvg = xAvg / 4;
        yAvg = yAvg / 4;
        canvas.drawCircle((float)(xAvg*x_sf), (float)(yAvg*y_sf), 50, averagePaint);
        canvas.drawCircle((float)(trilaterationPos[0]*x_sf), (float)(trilaterationPos[1]*y_sf), 10, locationPaint);
        canvas.drawCircle((float)(trilaterationPos2[0]*x_sf), (float)(trilaterationPos2[1]*y_sf), 10, locationPaint);
        canvas.drawCircle((float)(trilaterationPos3[0]*x_sf), (float)(trilaterationPos3[1]*y_sf), 10, locationPaint);
        canvas.drawCircle((float)(trilaterationPos4[0]*x_sf), (float)(trilaterationPos4[1]*y_sf), 10, locationPaint);
        canvas.drawText(String.format("X: %.2f Y: %.2f", xAvg, yAvg), (float)(trilaterationPos[0]*x_sf-50),(float)(trilaterationPos[1]*y_sf+150), textPaint);
    }

    private void setupPaint() {
        beaconPaint = new Paint();
        beaconPaint.setColor(beaconPaintColour);
        beaconPaint.setAntiAlias(true);
        beaconPaint.setStrokeWidth(5);
        beaconPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        beaconPaint.setStrokeJoin(Paint.Join.ROUND);
        beaconPaint.setStrokeCap(Paint.Cap.ROUND);

        locationPaint = new Paint();
        locationPaint.setColor(locationPaintColour);
        locationPaint.setAntiAlias(true);
        locationPaint.setStrokeWidth(5);
        locationPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        locationPaint.setStrokeJoin(Paint.Join.ROUND);
        locationPaint.setStrokeCap(Paint.Cap.ROUND);

        averagePaint = new Paint();
        averagePaint.setColor(Color.YELLOW);
        averagePaint.setAntiAlias(true);
        averagePaint.setStrokeWidth(5);
        averagePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        averagePaint.setStrokeJoin(Paint.Join.ROUND);
        averagePaint.setStrokeCap(Paint.Cap.ROUND);

        rangePaint = new Paint();
        rangePaint.setColor(rangePaintColour);
        rangePaint.setAntiAlias(true);
        rangePaint.setStrokeWidth(3);
        rangePaint.setStyle(Paint.Style.STROKE);
        rangePaint.setStrokeJoin(Paint.Join.ROUND);
        rangePaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(3);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setTextSize(40);
    }
}
