package com.kalbe.kalbeproject.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kalbe.core.ui.BaseActivity
import com.kalbe.kalbeproject.R

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}