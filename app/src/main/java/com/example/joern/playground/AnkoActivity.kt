package com.example.joern.playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import org.jetbrains.anko.dip
import org.jetbrains.anko.padding
import org.jetbrains.anko.sp
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class AnkoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            padding = dip(8)

            textView("Hi! gg cc") {
                typeface = ResourcesCompat.getFont(this@AnkoActivity, R.font.it)
                textSize = sp(15).toFloat()
            }

            textView("You! gg cc") {
                textSize = sp(16).toFloat()
                typeface = ResourcesCompat.getFont(this@AnkoActivity, R.font.semibold)
            }

            textView("whazzup? gg cc") {
                textSize = sp(14).toFloat()
                typeface = ResourcesCompat.getFont(this@AnkoActivity, R.font.light)
            }
        }
    }
}
