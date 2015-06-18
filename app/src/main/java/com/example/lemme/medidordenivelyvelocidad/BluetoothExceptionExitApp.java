package com.example.lemme.medidordenivelyvelocidad;

import android.app.Activity;

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