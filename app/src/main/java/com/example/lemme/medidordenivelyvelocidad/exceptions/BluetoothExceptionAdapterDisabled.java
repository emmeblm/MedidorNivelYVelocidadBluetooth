package com.example.lemme.medidordenivelyvelocidad.exceptions;

import android.app.Activity;

import com.example.lemme.medidordenivelyvelocidad.activities.MainActivity;
import com.example.lemme.medidordenivelyvelocidad.commons.Utilities;
import com.example.lemme.medidordenivelyvelocidad.exceptions.BluetoothException;

/**
 * Created by lemme on 5/12/15.
 */
public class BluetoothExceptionAdapterDisabled extends BluetoothException {

    public BluetoothExceptionAdapterDisabled() {
        super(Utilities.REQUEST_USER, Utilities.BLUETOOTH_ADAPTER_DISABLED);
    }

    @Override
    public void manageException(Activity activity) {
        MainActivity mainActivity = (MainActivity) activity;
        mainActivity.promptUserToEnableBluetooth();
    }
}
