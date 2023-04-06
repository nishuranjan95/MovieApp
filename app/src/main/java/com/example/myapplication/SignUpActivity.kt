package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        phoneCheck()
    }

    private fun phoneCheck() {
        binding.btnNext.setOnClickListener{
            val text=binding.etPhone.text.toString()
            if(text.length<10){
                Toast.makeText(this,"Please Enter Valid Number",Toast.LENGTH_SHORT).show()
            } else{
                val intent=Intent(this@SignUpActivity,OTPVerificationActivity::class.java)
                intent.putExtra("mobile",text)
                startActivity(intent)
            }
        }
    }
}