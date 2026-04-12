package com.example.laboratorio_1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        val message = intent.getStringExtra("EXTRA_MESSAGE")
        findViewById<TextView>(R.id.textViewDisplay).text = message
    }
}