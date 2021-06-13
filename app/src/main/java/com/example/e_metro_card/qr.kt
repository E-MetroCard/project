package com.example.e_metro_card

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import kotlinx.android.synthetic.main.qr.*
import java.util.concurrent.Executor

class qr : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr)
        if (ContextCompat.checkSelfPermission(this@qr, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this@qr, arrayOf(Manifest.permission.CAMERA), 123)
        } else {
            val executor = ContextCompat.getMainExecutor(this)
            val biometricManager = BiometricManager.from(this)

            when (biometricManager.canAuthenticate()) {
                BiometricManager.BIOMETRIC_SUCCESS ->
                    authUser(executor)
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                    Toast.makeText(
                        this,
                        "Biometric not available",
                        Toast.LENGTH_LONG
                    ).show()
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                    Toast.makeText(
                        this,
                        "Biometric features are currently unavailable",
                        Toast.LENGTH_LONG
                    ).show()
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                    Toast.makeText(
                        this,
                        "The user hasnt associated any biometric credentials..",
                        Toast.LENGTH_LONG
                    ).show()
            }
            startScanning()
        }
    }

    private fun startScanning() {
        // Parameters (default values)
        val scannerView: CodeScannerView = findViewById(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show()
                startScanning()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(::codeScanner.isInitialized) {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        if(::codeScanner.isInitialized) {
            codeScanner.releaseResources()
        }
        super.onPause()
    }

    private fun authUser(executor: Executor) {
        // 1


        // 1
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                // 2
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT)
                        .show()
                }

                // 3
                override fun onAuthenticationError(
                    errorCode: Int, errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error ${errString}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // 4
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext,
                        "Authentication failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            // 2
            .setTitle("Auhthentication  required")
            // 3
            .setSubtitle("Important authentication")
            // 4
            .setDescription("Please authenticate to be able to view account inf...")
            // 5
            .setDeviceCredentialAllowed(true)
            // 6
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}



