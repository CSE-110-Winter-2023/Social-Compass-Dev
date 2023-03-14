package com.example.socialcompass;

import android.app.Activity;
import android.view.View;

import java.math.BigDecimal;
import java.math.MathContext;

public class ZoomLevel {

    private static ZoomLevel instance;
    private Activity activity;
    public static ZoomLevel singleton(Activity activity) {
        if (instance == null){
            instance = new ZoomLevel(activity);
        }
        return instance;
    }

    private double calc(double x) {

        return (210.316 + (-0.8349716 - 210.316)/(1 + Math.pow((x/92411.42),0.2862929)));

    }

    int zoomLevelNumber;
    final double[] increaseScale = new double[]{1, 4/3, 1.5, 2};
    private float renderScale = 1;

    public ZoomLevel(Activity activity) {
        this.zoomLevelNumber = 0;
        this.activity = activity;
    }

    public int calculateDistance(float trueDistance){
        var result = (int) (calc(trueDistance)*renderScale);
        System.out.println(trueDistance + " >>>>>>>>>>>>>>> " + result + " : " + renderScale);
        return (int) (result * this.activity.getResources().getDisplayMetrics().scaledDensity);
    }

    public int getZoomLevelNumber() {
        return zoomLevelNumber;
    }

    public void setZoomLevelNumber(int zoomLevelNumber) {
        this.zoomLevelNumber = 0;
        this.renderScale = 1;
        for (int i = 0; i < zoomLevelNumber; i++){
            incrementZoomLevel();
        }
    }

    public void incrementZoomLevel(){
        renderScale *= increaseScale[zoomLevelNumber];
        zoomLevelNumber++;


        var circle25 = this.activity.findViewById(R.id.circle_25);
        var circle50 = this.activity.findViewById(R.id.circle_50);
        var circle75 = this.activity.findViewById(R.id.circle_75);
        var circle100 = this.activity.findViewById(R.id.circle_100);

        var con = new View[]{circle25, circle50, circle75, circle100};
        for (var v : con) {
            v.setScaleX((float) (v.getScaleX() * increaseScale[zoomLevelNumber-1]));
            v.setScaleY((float) (v.getScaleY() * increaseScale[zoomLevelNumber-1]));
        }
    }

    public void decreaseZoomLevel(){
        zoomLevelNumber--;
        renderScale /= increaseScale[zoomLevelNumber];

        var circle25 = this.activity.findViewById(R.id.circle_25);
        var circle50 = this.activity.findViewById(R.id.circle_50);
        var circle75 = this.activity.findViewById(R.id.circle_75);
        var circle100 = this.activity.findViewById(R.id.circle_100);

        var con = new View[]{circle25, circle50, circle75, circle100};
        for (var v : con) {
            v.setScaleX((float) (v.getScaleX() / increaseScale[zoomLevelNumber]));
            v.setScaleY((float) (v.getScaleY() / increaseScale[zoomLevelNumber]));
        }
    }


}
