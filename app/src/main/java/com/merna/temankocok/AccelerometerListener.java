package com.merna.temankocok;

/**
 * Created by Merna on 6/18/2018.
 */

public interface AccelerometerListener {
    public void onAccelerationChanged(float x, float y, float z);
    public void onShake(float force);

}
