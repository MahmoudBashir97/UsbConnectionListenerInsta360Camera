package com.mahmoudbashir.usblistenerexample

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log
import android.widget.Toast

class UsbReceiver : BroadcastReceiver() {
    companion object {
        const val ACTION_USB_PERMISSION = "com.mahmoudbashir.usblistenerexample.USB_PERMISSION"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("UsbReceiver", "Received intent with action: ${intent?.action} , ${intent?.data}")
        when (intent?.action) {
            UsbManager.ACTION_USB_DEVICE_ATTACHED -> handleDeviceAttached(context, intent)
            UsbReceiver.ACTION_USB_PERMISSION -> handlePermissionIntent(context, intent)
        }

        //handleUsbDeviceAttached(context!!,intent!!)
        /* val usbManager = context?.getSystemService(Context.USB_SERVICE) as UsbManager
         val action: String? = intent?.action
         if (UsbManager.ACTION_USB_DEVICE_ATTACHED == action){
             val device:UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)

             device?.let {
                 if (!usbManager.hasPermission(it)) {
                     val permissionIntent = PendingIntent.getBroadcast(context, 0, Intent(ACTION_USB_PERMISSION), PendingIntent.FLAG_UPDATE_CURRENT)
                     usbManager.requestPermission(it, permissionIntent)
                 } else {
                     Toast.makeText(context, "Permission already granted for ${it.deviceName}", Toast.LENGTH_SHORT).show()
                 }
             }
         }*/
    }

    private fun handlePermissionIntent(context: Context?, intent: Intent?) {
        val device: UsbDevice? = intent?.getParcelableExtra(UsbManager.EXTRA_DEVICE)
        val permissionGranted = intent?.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)

        if (permissionGranted == true && device != null) {
            Toast.makeText(
                context,
                "Permission granted for ${device.deviceName}",
                Toast.LENGTH_SHORT
            ).show()
            // Proceed with further actions, e.g., accessing the USB device
        } else {
            Toast.makeText(context, "Permission denied for USB device", Toast.LENGTH_SHORT).show()
        }
    }


    private fun handleDeviceAttached(context: Context?, intent: Intent?) {
        val usbManager = context?.getSystemService(Context.USB_SERVICE) as UsbManager
        val device = intent?.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
        val i = Intent().apply {
            action = UsbReceiver.ACTION_USB_PERMISSION
        }

        device?.let { it ->
            if (!usbManager.hasPermission(it)) {
                val permissionIntent = PendingIntent.getBroadcast(
                    context, 1, Intent(UsbReceiver.ACTION_USB_PERMISSION).also { extra ->
                        extra.putExtra(UsbManager.EXTRA_DEVICE, device)
                    }, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                usbManager.requestPermission(it, permissionIntent)
            } else {
                Toast.makeText(context, "Permission already granted.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logErrors(message: String? = null) {
        Log.d("TrackingUsbReceiver: ", "TrackingUsbReceiver message : $message")

    }

}