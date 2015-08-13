package com.worldline.openxcmanagers.sdk;

import com.google.gson.annotations.SerializedName;

/**
 * Created by a557114 on 13/08/2015.
 */
public class OpenXCResponse {
    @SerializedName("accelerator_pedal_position")
    public Integer acceleratorPedalPosition;
    @SerializedName("parking_brake_status")
    public Boolean parkingBrakeStatus;
    @SerializedName("fuel_consumed_since_restart")
    public Double fuelConsumedSinceRestart;
    @SerializedName("manual_trans")
    public Boolean manualTrans;
    @SerializedName("ignition_status")
    public String ignitionStatus;
    @SerializedName("gear_lever_position")
    public String gearLeverPosition;
    @SerializedName("torque_at_transmission")
    public Double torqueAtTransmission;
    @SerializedName("engine_running")
    public Boolean engineRunning;
    @SerializedName("transmission_gear_int")
    public Integer transmissionGearInt;
    public Double longitude;
    public Double odometer;
    public Integer brake;
    @SerializedName("vehicle_speed")
    public Double vehicleSpeed;
    @SerializedName("transmission_gear_position")
    public String transmissionGearPosition;
    @SerializedName("brake_pedal_status")
    public Boolean brakePedalStatus;
    public Double latitude;
    @SerializedName("fuel_level")
    public Double fuelLevel;
    @SerializedName("engine_speed")
    public Double engineSpeed;
    public Double heading;
    @SerializedName("steering_wheel_angle")
    public Integer steeringWheelAngle;
}
