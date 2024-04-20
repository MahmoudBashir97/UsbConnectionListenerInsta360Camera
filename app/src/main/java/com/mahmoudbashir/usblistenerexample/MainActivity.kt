package com.mahmoudbashir.usblistenerexample

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
   // private lateinit var usbReceiver: UsbReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //usbReceiver = UsbReceiver()
        checkForUsbDevicesAndRequestPermission()
    }

    private fun checkForUsbDevicesAndRequestPermission() {
        val usbManager = getSystemService(Context.USB_SERVICE) as UsbManager
        val deviceList = usbManager.deviceList

        if (deviceList.isEmpty()) {
            Toast.makeText(this, "No USB devices connected", Toast.LENGTH_LONG).show()
        } else {
            for (device in deviceList.values) {
                if (!usbManager.hasPermission(device)) {
                    requestUsbPermission(usbManager, device)
                } else {
                    Toast.makeText(this, "Already have permission for ${device.productName}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun requestUsbPermission(usbManager: UsbManager, device: UsbDevice) {
        val permissionIntent = PendingIntent.getBroadcast(this, 0, Intent(ACTION_USB_PERMISSION), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        usbManager.requestPermission(device, permissionIntent)

        // Displaying a toast for the user's information. Ideally, you may want to show a dialog.
        Toast.makeText(this, "Requesting permission for ${device.deviceName}", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val ACTION_USB_PERMISSION = "com.yourapp.package.ACTION_USB_PERMISSION"
    }


   /* private fun registerUsbReceiver(){
        IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED).also {
            it.addAction(UsbReceiver.ACTION_USB_PERMISSION)
            registerReceiver(usbReceiver, it,RECEIVER_NOT_EXPORTED)
        }
    }

    private fun unRegisterUsbReceiver(){
        unregisterReceiver(usbReceiver)
    }

    override fun onStart() {
        super.onStart()
        registerUsbReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterUsbReceiver()
    }*/
}