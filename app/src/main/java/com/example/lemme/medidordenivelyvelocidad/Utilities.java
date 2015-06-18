package com.example.lemme.medidordenivelyvelocidad;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothSocket;

import java.util.UUID;

/**
 * Created by lemme on 5/7/15.
 */
public abstract class Utilities {

    public static final String TAG = "BluetoothConnection";
    public static final UUID SPP_UUID_SERVICE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static final String BLUETOOTH_MODULE_MAC_ADDRESS = "30:15:01:13:15:03";
    public static final String FATAL_ERROR = "Fatal Error";
    public static final String BLUETOOTH_NOT_SUPPORTED = "Device does not support Bluetooth";
    public static final String INSECURE_RFCOMM_CONNECTION_FAILED = "Could not create Insecure RFComm Connection";
    public static final String ERROR_MESSAGE_JOIN_CHAR = " - ";
    public static final String BLUETOOTH_ON = "... Bluetooth On ...";
    public static final String REQUEST_USER = "Request User";
    public static final String BLUETOOTH_ADAPTER_DISABLED = "Bluetooth Adapter Disabled. Prompt User to Enable Bluetooth.";
    public static final int RECEIVE_MESSAGE = 1;
    public static final int MINIMUM_SDK_VERSION = 10;
    public static final String SUCCESSFUL_CONNECTION = "Connection successfully established";
    public static final String CONNECTION_FAILED = "Connection Failed";
    public static final String UNABLE_TO_CLOSE_SOCKET = "Unable to close socket during connection failure";
    public static final String ERROR_SENDING_DATA = "Error sending data";
    public static final int MAXIMUM_LENGHT_DATA_SERIE_DISPLAYED = 10;
    public static final int ENABLE_BLUETOOTH_REQUEST = 1;
    public static final int SENSOR_SAMPLING_STEP = 1;
    public static final Integer MIN_Y_AXIS_VALUE_SPEEDOMETER = 200;
    public static final Integer MAX_Y_AXIS_VALUE_SPEEDOMETER = 1030;
    public static final Integer MIN_Y_AXIS_VALUE_LEVEL_METER = 200;
    public static final Integer MAX_Y_AXIS_VALUE_LEVEL_METER = 1030;


    public static BluetoothSocket bluetoothSocket;
}
