package com.example.e_metro_card


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import kotlinx.android.synthetic.main.qr.*

class qr : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    val MY_CAMERA_PERMISSION_REQUEST = 1111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr)

        codeScanner = CodeScanner(this,scannerView)

        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.isFlashEnabled = true
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Scan Result: ${it.text}",Toast.LENGTH_LONG).show()
            }
        }

        codeScanner.errorCallback = ErrorCallback{
            runOnUiThread {
                Toast.makeText(this,"Camera Error: ${it.message}",Toast.LENGTH_LONG).show()
            }
        }

        fun checkPermission()
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_REQUEST)
            }
            else
            {
                codeScanner.startPreview()
            }
        }


        fun onRequestPermissionsResult(requestCode:Int,permissions:Array<out String>,grantResults:IntArray)
        {
            if(requestCode==MY_CAMERA_PERMISSION_REQUEST&&grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                codeScanner.startPreview()
            }
            else
            {
            Toast.makeText(this,"Can not scan until you give the permission suar",Toast.LENGTH_LONG).show()
            }

        }



        fun onResume() {
            super.onResume()
            codeScanner.startPreview()

        }

        fun onPause() {
            super.onPause()
            codeScanner.releaseResources()
        }
    }

}


