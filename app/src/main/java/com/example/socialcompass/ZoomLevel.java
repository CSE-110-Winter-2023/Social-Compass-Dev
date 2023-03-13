package com.example.socialcompass;

import static com.example.socialcompass.ZoomManager.getOwnLocation;

import android.app.Activity;
import android.location.Location;
import android.view.View;

public interface ZoomLevel {
    public void setUI(Activity activity);
    public void setDistances();
}

class Zoom1 implements ZoomLevel {
    public void setUI(Activity activity){
        var circle33 = activity.findViewById(R.id.circle_33);
        var circle66 = activity.findViewById(R.id.circle_66);
        var circle25 = activity.findViewById(R.id.circle_25);
        var circle50 = activity.findViewById(R.id.circle_50);
        var circle75 = activity.findViewById(R.id.circle_75);
        var circle100 = activity.findViewById(R.id.circle_100);
        circle33.setVisibility(View.INVISIBLE);
        circle66.setVisibility(View.INVISIBLE);
        circle25.setVisibility(View.INVISIBLE);
        circle50.setVisibility(View.INVISIBLE);
        circle75.setVisibility(View.INVISIBLE);
        circle100.setVisibility(View.VISIBLE);
    }

    public void setDistances(){
        Location ownLocation = getOwnLocation();

        for(CompassLocationObject co : CompassLocationContainer.singleton()){

            float distance = co.getLocation().distanceTo(ownLocation);
            CompassUIController controller = co.getController();

            if(distance <= 1609.34) {
                controller.setDistance(240); //55 percent
            }
            else {
                controller.setDistance(560); // on radius
                //TODO turn into a dot
            }
        }
    }
}

class Zoom2 implements ZoomLevel {
    public void setUI(Activity activity){
        var circle33 = activity.findViewById(R.id.circle_33);
        var circle66 = activity.findViewById(R.id.circle_66);
        var circle25 = activity.findViewById(R.id.circle_25);
        var circle50 = activity.findViewById(R.id.circle_50);
        var circle75 = activity.findViewById(R.id.circle_75);
        var circle100 = activity.findViewById(R.id.circle_100);
        circle33.setVisibility(View.INVISIBLE);
        circle66.setVisibility(View.INVISIBLE);
        circle25.setVisibility(View.INVISIBLE);
        circle50.setVisibility(View.VISIBLE);
        circle75.setVisibility(View.INVISIBLE);
        circle100.setVisibility(View.VISIBLE);
    }
    public void setDistances(){
        Location ownLocation = getOwnLocation();

        for(CompassLocationObject co : CompassLocationContainer.singleton()){

            float distance = co.getLocation().distanceTo(ownLocation);
            CompassUIController controller = co.getController();

            if(distance <= 1609.34) {
                controller.setDistance(33); //33 percent
            }
            else if(distance <= 16093.4) {
                controller.setDistance(66); //66 percent
            }
            else {
                controller.setDistance(560); // on radius
                //TODO turn into a dot
            }
        }
    }
}

class Zoom3 implements ZoomLevel {
    public void setUI(Activity activity){
        var circle33 = activity.findViewById(R.id.circle_33);
        var circle66 = activity.findViewById(R.id.circle_66);
        var circle25 = activity.findViewById(R.id.circle_25);
        var circle50 = activity.findViewById(R.id.circle_50);
        var circle75 = activity.findViewById(R.id.circle_75);
        var circle100 = activity.findViewById(R.id.circle_100);
        circle33.setVisibility(View.VISIBLE);
        circle66.setVisibility(View.VISIBLE);
        circle25.setVisibility(View.INVISIBLE);
        circle50.setVisibility(View.INVISIBLE);
        circle75.setVisibility(View.INVISIBLE);
        circle100.setVisibility(View.VISIBLE);
    }
    public void setDistances(){
        Location ownLocation = getOwnLocation();

        for(CompassLocationObject co : CompassLocationContainer.singleton()){

            float distance = co.getLocation().distanceTo(ownLocation);
            CompassUIController controller = co.getController();

            if(distance <= 1609.34) {
                controller.setDistance(25); //25 percent
            }
            else if(distance <= 16093.4) {
                controller.setDistance(240); //50 percent
            }
            else if(distance <= 804672) {
                controller.setDistance(75); //75 percent
            }
            else {
                controller.setDistance(560); // on radius
                //TODO turn into a dot
            }
        }
    }
}

class Zoom4 implements ZoomLevel {
    public void setUI(Activity activity){
        var circle33 = activity.findViewById(R.id.circle_33);
        var circle66 = activity.findViewById(R.id.circle_66);
        var circle25 = activity.findViewById(R.id.circle_25);
        var circle50 = activity.findViewById(R.id.circle_50);
        var circle75 = activity.findViewById(R.id.circle_75);
        var circle100 = activity.findViewById(R.id.circle_100);
        circle33.setVisibility(View.INVISIBLE);
        circle66.setVisibility(View.INVISIBLE);
        circle25.setVisibility(View.VISIBLE);
        circle50.setVisibility(View.VISIBLE);
        circle75.setVisibility(View.VISIBLE);
        circle100.setVisibility(View.VISIBLE);
    }

    public void setDistances(){
        Location ownLocation = getOwnLocation();

        for(CompassLocationObject co : CompassLocationContainer.singleton()){

            float distance = co.getLocation().distanceTo(ownLocation);
            CompassUIController controller = co.getController();

            if(distance <= 1609.34) {
                controller.setDistance(20); //20 percent
            }
            else if(distance <= 16093.4) {
                controller.setDistance(40); //40 percent
            }
            else if(distance <= 804672) {
                controller.setDistance(60); //60 percent
            }
            else if(distance <= 6.378e+6) {
                controller.setDistance(80); //80 percent
            }
            else {
                controller.setDistance(560); // on radius
                //TODO turn into a dot
            }
        }
    }
}