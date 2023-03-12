package com.example.socialcompass;

import static com.example.socialcompass.ZoomManager.getOwnLocation;

import android.location.Location;

public interface ZoomLevel {
    public void setUI();
    public void setDistances();
}

class Zoom1 implements ZoomLevel {
    public void setUI(){

    }

    public void setDistances(){
        Location ownLocation = getOwnLocation();

        for(CompassLocationObject co : CompassLocationContainer.singleton()){

            float distance = co.getLocation().distanceTo(ownLocation);
            CompassUIController controller = co.getController();

            if(distance <= 1609.34) {
                controller.setDistance(55); //55 percent
            }
            else {
                controller.setDistance(100); // on radius
                //TODO turn into a dot
            }
        }
    }
}

class Zoom2 implements ZoomLevel {
    public void setUI(){

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
                controller.setDistance(100); // on radius
                //TODO turn into a dot
            }
        }
    }
}

class Zoom3 implements ZoomLevel {
    public void setUI(){

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
                controller.setDistance(50); //50 percent
            }
            else if(distance <= 804672) {
                controller.setDistance(75); //75 percent
            }
            else {
                controller.setDistance(100); // on radius
                //TODO turn into a dot
            }
        }
    }
}

class Zoom4 implements ZoomLevel {
    public void setUI(){

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
                controller.setDistance(100); // on radius
                //TODO turn into a dot
            }
        }
    }
}