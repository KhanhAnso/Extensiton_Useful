package com.example.useful_extensions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Extension #1
        Log.d("$TAG","Id : ${generateUniqueId()}")

        //Extension #2
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.fruitOnValueChanged().observeOne(this, Observer {
            Log.d("$TAG","Fruit : $it")
        })

        //Extension #3
        Log.d("$TAG","hasPermissionsAndGranted : ${hasPermissionsAndGranted()}")

        //Extension #4 : Bật tự động đóng keyboard khi chạm (touch) ra ngoài UI
        setAutoHideKeyboardListener(applicationContext, findViewById<View>(android.R.id.content).rootView)

        //Extension #5
        Log.d("$TAG","Date Time Today : ${getDateTimeToday()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        //Xóa tự động tắt keyboard khi chạm (touch) ra ngoài
        removeAutoHideKeyboardListener(findViewById<View>(android.R.id.content).rootView)
    }
}