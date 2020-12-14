package com.example.pizzagood.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.pizzagood.MainActivity
import com.example.pizzagood.R

class Finish : AppCompatActivity() {
    var button: Button? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.finish)
        title = "Finish"

        button = findViewById(R.id.button_ok) as Button
        button!!.setOnClickListener { openNewActivity() }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun openNewActivity() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        // calculatePrice(tv1)
    }
}