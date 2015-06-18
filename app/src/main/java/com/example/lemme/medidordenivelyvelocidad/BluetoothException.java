package com.example.lemme.medidordenivelyvelocidad;

import android.app.Activity;

/**
 * Created by lemme on 5/12/15.
 */
public abstract class BluetoothException extends Exception {
    private String type;
    private String message;

    protected BluetoothException(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public abstract void manageException(Activity activity);

    @Override
    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
