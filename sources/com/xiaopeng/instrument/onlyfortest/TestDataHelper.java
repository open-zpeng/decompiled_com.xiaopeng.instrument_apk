package com.xiaopeng.instrument.onlyfortest;

import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.utils.XILog;
/* loaded from: classes.dex */
public class TestDataHelper {
    public static final String APPOINT_HOUR = "appoint_hour";
    public static final String APPOINT_MIN = "appoint_min";
    public static final String AVERAGE_ENERGY_COST = "a_energy";
    public static final String BATTERY_LEVEL = "battery";
    public static final String BATTERY_LIFE_STANDARD = "bl_standard";
    public static final String CHARGE_CURRENT = "current";
    public static final String CHARGE_VOLTAGE = "voltage";
    public static final String DIS_AFTER_START = "dis_after_start";
    public static final String DRIVE_MODE = "drive_mode";
    public static final String ENDURANCE_MILEAGE = "endurance_mileage";
    public static final String ENGINE_COVER_STATE = "ec_state";
    public static final String GEAR_LEV = "GEAR_LEV";
    public static final String LEFT_BACK_DOOR_STATE = "lb_door_state";
    public static final String LEFT_BACK_TIRE_PRESSURE = "lb_tire_pressure";
    public static final String LEFT_BACK_TIRE_STATE = "lb_tire_state";
    public static final String LEFT_BACK_TIRE_TEMPERATURE = "lb_tire_temp";
    public static final String LEFT_FRONT_DOOR_STATE = "lf_door_state";
    public static final String LEFT_FRONT_TIRE_PRESSURE = "lf_tire_pressure";
    public static final String LEFT_FRONT_TIRE_STATE = "lf_tire_state";
    public static final String LEFT_FRONT_TIRE_TEMPERATURE = "lf_tire_temp";
    public static final String OUT_TEMP = "out_temp";
    public static final String READY = "READY";
    public static final String RIGHT_BACK_DOOR_STATE = "rb_door_state";
    public static final String RIGHT_BACK_TIRE_PRESSURE = "rb_tire_pressure";
    public static final String RIGHT_BACK_TIRE_STATE = "rb_tire_state";
    public static final String RIGHT_BACK_TIRE_TEMPERATURE = "rb_tire_temp";
    public static final String RIGHT_FRONT_DOOR_STATE = "rf_door_state";
    public static final String RIGHT_FRONT_TIRE_PRESSURE = "rf_tire_pressure";
    public static final String RIGHT_FRONT_TIRE_STATE = "rf_tire_state";
    public static final String RIGHT_FRONT_TIRE_TEMPERATURE = "rf_tire_temp";
    public static final String TIME = "time";
    public static final String TIME_MORNING_OR_NOON = "time_mn";
    public static final String TIME_PATTERN = "time_mode";
    public static final String TOTAL_DIS = "total_dis";
    public static final String TRUNK_COVER_STATE = "tc_state";

    public static void handleData(String str, Object obj) {
        XILog.d("Test", "handleData key:" + str + "   data:" + obj);
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2144637013:
                if (str.equals(RIGHT_FRONT_DOOR_STATE)) {
                    c = 0;
                    break;
                }
                break;
            case -2061744307:
                if (str.equals(GEAR_LEV)) {
                    c = 1;
                    break;
                }
                break;
            case -1475404844:
                if (str.equals(APPOINT_MIN)) {
                    c = 2;
                    break;
                }
                break;
            case -1336332250:
                if (str.equals(AVERAGE_ENERGY_COST)) {
                    c = 3;
                    break;
                }
                break;
            case -1313927085:
                if (str.equals(TIME_MORNING_OR_NOON)) {
                    c = 4;
                    break;
                }
                break;
            case -1269574039:
                if (str.equals(RIGHT_BACK_TIRE_STATE)) {
                    c = 5;
                    break;
                }
                break;
            case -1257292175:
                if (str.equals(RIGHT_FRONT_TIRE_PRESSURE)) {
                    c = 6;
                    break;
                }
                break;
            case -900549847:
                if (str.equals(LEFT_BACK_DOOR_STATE)) {
                    c = 7;
                    break;
                }
                break;
            case -753243163:
                if (str.equals(RIGHT_FRONT_TIRE_STATE)) {
                    c = '\b';
                    break;
                }
                break;
            case -576959213:
                if (str.equals(TOTAL_DIS)) {
                    c = '\t';
                    break;
                }
                break;
            case -546908658:
                if (str.equals(DIS_AFTER_START)) {
                    c = '\n';
                    break;
                }
                break;
            case -384218971:
                if (str.equals(LEFT_FRONT_DOOR_STATE)) {
                    c = 11;
                    break;
                }
                break;
            case -331239923:
                if (str.equals(BATTERY_LEVEL)) {
                    c = '\f';
                    break;
                }
                break;
            case -261245246:
                if (str.equals(LEFT_BACK_TIRE_TEMPERATURE)) {
                    c = '\r';
                    break;
                }
                break;
            case -235044206:
                if (str.equals(ENDURANCE_MILEAGE)) {
                    c = 14;
                    break;
                }
                break;
            case 3560141:
                if (str.equals(TIME)) {
                    c = 15;
                    break;
                }
                break;
            case 36460501:
                if (str.equals(TIME_PATTERN)) {
                    c = 16;
                    break;
                }
                break;
            case 54439461:
                if (str.equals(OUT_TEMP)) {
                    c = 17;
                    break;
                }
                break;
            case 70672947:
                if (str.equals(LEFT_BACK_TIRE_PRESSURE)) {
                    c = 18;
                    break;
                }
                break;
            case 77848963:
                if (str.equals(READY)) {
                    c = 19;
                    break;
                }
                break;
            case 428262832:
                if (str.equals(ENGINE_COVER_STATE)) {
                    c = 20;
                    break;
                }
                break;
            case 490844003:
                if (str.equals(LEFT_BACK_TIRE_STATE)) {
                    c = 21;
                    break;
                }
                break;
            case 513251068:
                if (str.equals(RIGHT_BACK_TIRE_TEMPERATURE)) {
                    c = 22;
                    break;
                }
                break;
            case 577486648:
                if (str.equals(DRIVE_MODE)) {
                    c = 23;
                    break;
                }
                break;
            case 632380254:
                if (str.equals(CHARGE_VOLTAGE)) {
                    c = 24;
                    break;
                }
                break;
            case 800614081:
                if (str.equals(TRUNK_COVER_STATE)) {
                    c = 25;
                    break;
                }
                break;
            case 1007174879:
                if (str.equals(LEFT_FRONT_TIRE_STATE)) {
                    c = 26;
                    break;
                }
                break;
            case 1126940025:
                if (str.equals(CHARGE_CURRENT)) {
                    c = 27;
                    break;
                }
                break;
            case 1140883910:
                if (str.equals(LEFT_FRONT_TIRE_TEMPERATURE)) {
                    c = 28;
                    break;
                }
                break;
            case 1302435181:
                if (str.equals(RIGHT_BACK_TIRE_PRESSURE)) {
                    c = 29;
                    break;
                }
                break;
            case 1506947234:
                if (str.equals(APPOINT_HOUR)) {
                    c = 30;
                    break;
                }
                break;
            case 1633999407:
                if (str.equals(RIGHT_BACK_DOOR_STATE)) {
                    c = 31;
                    break;
                }
                break;
            case 1646950162:
                if (str.equals(BATTERY_LIFE_STANDARD)) {
                    c = ' ';
                    break;
                }
                break;
            case 1805912887:
                if (str.equals(LEFT_FRONT_TIRE_PRESSURE)) {
                    c = '!';
                    break;
                }
                break;
            case 1915380224:
                if (str.equals(RIGHT_FRONT_TIRE_TEMPERATURE)) {
                    c = '\"';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                ClusterManager.getInstance().onDoorFR(((Integer) obj).intValue() != 0);
                return;
            case 1:
                ClusterManager.getInstance().onGear(((Integer) obj).intValue());
                return;
            case 2:
                ClusterManager.getInstance().onAppointmentMinute((String) obj);
                return;
            case 3:
                ClusterManager.getInstance().onPowerAverageEnergyConsumption(((Integer) obj).intValue());
                return;
            case 4:
                ClusterManager.getInstance().onMorningOrAfternoon(((Integer) obj).intValue());
                return;
            case 5:
                ClusterManager.getInstance().onTireBRTireState(((Integer) obj).intValue());
                return;
            case 6:
                ClusterManager.getInstance().onTireFRPressure((String) obj);
                return;
            case 7:
                ClusterManager.getInstance().onDoorBL(((Integer) obj).intValue() != 0);
                return;
            case '\b':
                ClusterManager.getInstance().onTireFRTireState(((Integer) obj).intValue());
                return;
            case '\t':
                ClusterManager.getInstance().onTotalValue((String) obj);
                return;
            case '\n':
                ClusterManager.getInstance().onThisTimeValue((String) obj);
                return;
            case 11:
                ClusterManager.getInstance().onDoorFL(((Integer) obj).intValue() != 0);
                return;
            case '\f':
                ClusterManager.getInstance().onElectricQuantity(((Integer) obj).intValue());
                return;
            case '\r':
                ClusterManager.getInstance().onTireBLTemperatures((String) obj);
                return;
            case 14:
                ClusterManager.getInstance().onEnduranceMileage(((Float) obj).floatValue());
                return;
            case 15:
                ClusterManager.getInstance().onTime((String) obj);
                return;
            case 16:
                ClusterManager.getInstance().onTimePattern(((Integer) obj).intValue());
                return;
            case 17:
                ClusterManager.getInstance().onTemperature(((Integer) obj).intValue());
                return;
            case 18:
                ClusterManager.getInstance().onTireBLPressure((String) obj);
                return;
            case 19:
                ClusterManager.getInstance().onReadyIndicatorLight(((Integer) obj).intValue() != 0);
                return;
            case 20:
                ClusterManager.getInstance().onHoodEngine(((Integer) obj).intValue() != 0);
                return;
            case 21:
                ClusterManager.getInstance().onTireBLTireState(((Integer) obj).intValue());
                return;
            case 22:
                ClusterManager.getInstance().onTireBRTemperatures((String) obj);
                return;
            case 23:
                ClusterManager.getInstance().onDrivingMode(((Integer) obj).intValue());
                return;
            case 24:
                ClusterManager.getInstance().onPowerInformationVoltage((String) obj);
                return;
            case 25:
                ClusterManager.getInstance().onHoodTrunk(((Integer) obj).intValue() != 0);
                return;
            case 26:
                ClusterManager.getInstance().onTireFLTireState(((Integer) obj).intValue());
                return;
            case 27:
                ClusterManager.getInstance().onPowerInformationCurrent((String) obj);
                return;
            case 28:
                ClusterManager.getInstance().onTireFLTemperatures((String) obj);
                return;
            case 29:
                ClusterManager.getInstance().onTireBRPressure((String) obj);
                return;
            case 30:
                ClusterManager.getInstance().onAppointmentHour((String) obj);
                return;
            case 31:
                ClusterManager.getInstance().onDoorBR(((Integer) obj).intValue() != 0);
                return;
            case ' ':
                ClusterManager.getInstance().onBatteryLifeStandard(((Integer) obj).intValue());
                return;
            case '!':
                ClusterManager.getInstance().onTireFLPressure((String) obj);
                return;
            case '\"':
                ClusterManager.getInstance().onTireFRTemperatures((String) obj);
                return;
            default:
                return;
        }
    }
}
