package com.example.lemme.medidordenivelyvelocidad;

import com.example.lemme.medidordenivelyvelocidad.activities.LevelMeterActivity;
import com.example.lemme.medidordenivelyvelocidad.activities.LevelMeterHistoricalActivity;
import com.example.lemme.medidordenivelyvelocidad.activities.SpeedometerActivity;
import com.example.lemme.medidordenivelyvelocidad.activities.SpeedometerHistoricalActivity;

/**
 * Created by lemme on 6/18/15.
 */
public class SensorClassFactory {
    private Class sensorClass;

    public Class create(ActivityTabHostType tabHostType, String sensorTypeTab) {

        switch (tabHostType) {
            case HISTORICAL:
                this.switchHistoricalSensorTypeTab(sensorTypeTab);
                break;
            case REAL_TIME_CHARTS:
                switchRealTimeChartsSensorTypeTab(sensorTypeTab);
                break;
            default:
                break;
        }
        return sensorClass;
    }

    private void switchHistoricalSensorTypeTab(String sensorTypeTab) {
        switch (sensorTypeTab) {
            case "speedometerTab":
                sensorClass = SpeedometerHistoricalActivity.class;
                break;
            case "levelMeterTab":
                sensorClass = LevelMeterHistoricalActivity.class;
                break;
            default:
                break;
        }
    }

    private void switchRealTimeChartsSensorTypeTab(String sensorTypeTab) {
        switch (sensorTypeTab) {
            case "speedometerTab":
                sensorClass = SpeedometerActivity.class;
                break;
            case "levelMeterTab":
                sensorClass = LevelMeterActivity.class;
                break;
            default:
                break;
        }
    }
}
