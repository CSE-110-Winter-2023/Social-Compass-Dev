package com.example.socialcompass;

import android.app.Activity;
import android.view.View;

public class ZoomLevel {

    private static ZoomLevel instance;
    private Activity activity;
    int zoomLevelNumber;
    final float[] increaseScale = {1, 4f/3f, 2, 4};
    static double[] distVisibility = {1609.34, 16093.4, 804672, 20000000};

    private float renderScale = 1;
    public static ZoomLevel singleton(Activity activity) {
        if (instance == null){
            instance = new ZoomLevel(activity);
        }
        return instance;
    }

    /**
     * Computes 389.8402 + (-2.230702e-10 - 389.8402)/(1 + (x/127.2712)^9.035292)^0.005427659
     * This is a 4 Parameter Logistic Fit of each circle range to screen dp.
     *
     * @param x
     * @return Distance in dp
     */
    private double calc(double x) {
        return (210.316 + (-0.8349716 - 210.316)/(1 + Math.pow((x/92411.42),0.2862929)));
    }

    public ZoomLevel(Activity activity) {
        this.zoomLevelNumber = 0;
        this.activity = activity;
        this.updateUI();
        this.incrementZoomLevel();
        this.incrementZoomLevel();
    }

    public int calculateDistance(float trueDistance){
        var result = (int) (calc(trueDistance) * renderScale * this.activity.getResources().getDisplayMetrics().scaledDensity);
        System.out.println(trueDistance + " >>>>>>>>>>>>>>> " + result + " : " + renderScale + " : " + this.zoomLevelNumber);
        return result;
    }

    public int getZoomLevelNumber() {
        return zoomLevelNumber;
    }

    public void setZoomLevelNumber(int zoomLevelNumber) {
        this.zoomLevelNumber = zoomLevelNumber;
        this.renderScale = this.increaseScale[this.zoomLevelNumber];
    }

    public boolean distanceInView(float trueDistance) {
        return (trueDistance < distVisibility[3-zoomLevelNumber]);
    }

    private void updateUI() {
        var circle25 = this.activity.findViewById(R.id.circle_25);
        var circle50 = this.activity.findViewById(R.id.circle_50);
        var circle75 = this.activity.findViewById(R.id.circle_75);
        var circle100 = this.activity.findViewById(R.id.circle_100);

        var con = new View[]{circle25, circle50, circle75, circle100};
        for (int i = 0; i < con.length; i++) {
            con[i].setScaleX(this.renderScale);
            con[i].setScaleY(this.renderScale);

            if (i < 4 - zoomLevelNumber) {
                con[i].setVisibility(View.VISIBLE);
            } else {
                con[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void incrementZoomLevel(){
        zoomLevelNumber++;
        renderScale = increaseScale[zoomLevelNumber];

        this.updateUI();
    }

    public void decreaseZoomLevel(){
        zoomLevelNumber--;
        renderScale = increaseScale[zoomLevelNumber];

        this.updateUI();
    }


}
