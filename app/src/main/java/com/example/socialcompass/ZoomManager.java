package com.example.socialcompass;

import android.location.Location;

public class ZoomManager {
    int zoomLevelNumber;

    public int getZoomLevelNumber() {
        return zoomLevelNumber;
    }

    public void setZoomLevelNumber(int zoomLevelNumber) {
        this.zoomLevelNumber = zoomLevelNumber;
    }

    public ZoomLevel zoomChanged(int zoomLevel){
        ZoomLevel zoomlevel;
        switch (zoomLevel){
            case 1:
                setZoomLevelNumber(1);
                zoomlevel = new Zoom1();
                System.out.println("ZoomLevel is 1");
                break;
            case 2:
                setZoomLevelNumber(2);
                zoomlevel = new Zoom2();
                System.out.println("ZoomLevel is 2");
                break;
            case 3:
                setZoomLevelNumber(3);
                zoomlevel = new Zoom3();
                System.out.println("ZoomLevel is 3");
                break;
            case 4:
                setZoomLevelNumber(4);
                zoomlevel = new Zoom4();
                System.out.println("ZoomLevel is 4");
                break;
            default:
                zoomlevel = null;
                System.out.println("ERROR");
                break;
        }
        return zoomlevel;
    }

    public static Location getOwnLocation(){
        Location currLocation = new Location("User Location");
        RemoteLocation remoteCurrLocation = LocationAPI.provide().getFromRemoteAPIAsync("team4PublicKey");

        currLocation.setLatitude(remoteCurrLocation.latitude);
        currLocation.setLongitude(remoteCurrLocation.longitude);

        return currLocation;
    }

}
