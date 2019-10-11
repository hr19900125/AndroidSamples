package com.hr.toy.kotlin

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.hr.toy.R

class HelloWorld : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_btn_and_textview)
        var tv = findViewById<TextView>(R.id.textview)
        tv.text = "Hello world!!"
    }
}