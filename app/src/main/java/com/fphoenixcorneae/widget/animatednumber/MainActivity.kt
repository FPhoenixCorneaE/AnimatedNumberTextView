package com.fphoenixcorneae.widget.animatednumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fphoenixcorneae.widget.AnimatedNumberTextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun start(view: View) {
        findViewById<AnimatedNumberTextView>(R.id.tvAnimatedNumber1).start()
        findViewById<AnimatedNumberTextView>(R.id.tvAnimatedNumber2).start()
        findViewById<AnimatedNumberTextView>(R.id.tvAnimatedNumber3).apply {
            numberAnimEnable = true
            start()
        }
        findViewById<AnimatedNumberTextView>(R.id.tvAnimatedNumber4).apply {
            numberStart = "0"
            numberEnd = "123456789"
            numberAnimEnable = true
            numberDuration = 2000
            numberPrefix = "-----"
            numberPostfix = "-----"
            numberFormatEnable = false
            start()
        }
    }
}