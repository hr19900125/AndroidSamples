package com.hr.toy.codesnippet

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hr.toy.R
import com.hr.toy.utils.PickImageUtils

class PickImageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_btn_and_textview)
        val button: Button = findViewById(R.id.btn)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = PickImageUtils.getPickImageChooserIntent(this@PickImageActivity, PickImageUtils.ImageSourceType.Gallery)
                startActivity(intent)
            }
        })
    }

}