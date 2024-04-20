package com.mahmoudbashir.usblistenerexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbManager
import android.widget.Toast

class UsbConnectionReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                Toast.makeText(context, "USB Device Connected", Toast.LENGTH_SHORT).show()
            }
            UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                Toast.makeText(context, "USB Device Disconnected", Toast.LENGTH_SHORT).show()
            }
        }
    }
}