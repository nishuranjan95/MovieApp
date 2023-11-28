package com.example.myapplication.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var REQUEST_CODE=1
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        requestPermission()
        phoneCheck()
    }

    private fun phoneCheck() {
        binding.btnNext.setOnClickListener{
            val text=binding.etPhone.text.toString()
            if(text.length<10){
                Toast.makeText(this,"Please Enter Valid Number",Toast.LENGTH_SHORT).show()
            } else{
                val intent=Intent(this@SignUpActivity, OTPVerificationActivity::class.java)
                intent.putExtra("mobile",text)
                startActivity(intent)
            }
        }
    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {result ->
            result.entries.forEach{
                if (it.key==Manifest.permission.CAMERA) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    if(it.value) {
                        Toast.makeText(this, "${it.key} granted", Toast.LENGTH_SHORT).show()
                    } else if(shouldShowRequestPermissionRationale(it.key)) alertBuilder(it.key)
                    else requestPermission()

                } else if (it.key==Manifest.permission.ACCESS_COARSE_LOCATION) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                    if(it.value) {
                        Toast.makeText(this, "${it.key} granted", Toast.LENGTH_SHORT).show()
                    } else if(shouldShowRequestPermissionRationale(it.key)) alertBuilder(it.key)
                    else requestPermission()
            }
            }

        }
    private fun requestPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_CODE
        )
    }

    private fun checkPermissions(): Boolean {
        return (
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.MANAGE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                )

    }
    private fun alertBuilder(permission:String){
        val builder = AlertDialog.Builder(this).setTitle("permission requested")
            .setMessage("$permission permission required")
        builder.setPositiveButton("Ok") { _, which ->
            val intent = Intent()
            intent.action = ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts(
                "package:%s",
                this.packageName, null
            )
            intent.data = uri
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        builder.setNegativeButton("No") { _, _ ->
            run {
                builder.setCancelable(true)
            }

        }
        builder.create()
        builder.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==REQUEST_CODE){
           if(grantResults.isNotEmpty()){
               if(grantResults[0]==PackageManager.PERMISSION_GRANTED){

               }
           }
        }

    }

}