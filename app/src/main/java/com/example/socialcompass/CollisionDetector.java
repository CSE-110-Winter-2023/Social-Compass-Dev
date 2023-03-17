package com.example.socialcompass;

import android.graphics.Rect;
import android.util.Log;

public class CollisionDetector {

    static public void collisionCheck(){
        CompassLocationContainer compassLocationContainer = CompassLocationContainer.singleton();

        for (int i = 0; i < compassLocationContainer.getAllLocations().size(); i++) {
            Rect rectOut = new Rect();
            CompassLocationObject compassLocationObjectOUT = compassLocationContainer.getAllLocations().get(i);

            compassLocationObjectOUT.getController().getTextView().getGlobalVisibleRect(rectOut);

            for (int j = i+1; j < compassLocationContainer.getAllLocations().size(); j++) {

                Rect rectIn = new Rect();
                CompassLocationObject compassLocationObjectIN = compassLocationContainer.getAllLocations().get(j);
                compassLocationObjectIN.getController().getTextView().getGlobalVisibleRect(rectIn);

                if(rectIn.intersect(rectOut)){
//
//                    compassLocationObjectOUT.getController().nudgeOUT();
//                    compassLocationObjectIN.getController().nudgeIN();
                }


            }
        }
    }

}
