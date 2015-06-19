package com.example.lemme.medidordenivelyvelocidad.exceptions;

import android.app.Activity;
import android.util.Log;

import com.example.lemme.medidordenivelyvelocidad.activities.MainActivity;
import com.example.lemme.medidordenivelyvelocidad.exceptions.BluetoothException;

/**
 * Created by lemme on 5/12/15.
 */
public class BluetoothExceptionExitApp extends BluetoothException {

    public BluetoothExceptionExitApp(String type, String message) {
        super(type, message);
    }

    @Override
    public void manageException(Activity activity) {
        MainActivity mainActivity = (MainActivity) activity;
        mainActivity.exitAppWithError(super.getType(), super.getMessage());
    }
}
