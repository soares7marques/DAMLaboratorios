package com.example.laboratorio_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val editText = findViewById<EditText>(R.id.texto)
        val button = findViewById<Button>(R.id.botao)

        button.setOnClickListener{
            val mensagem = editText.text.toString()
            if(mensagem.isNotEmpty()){
                val intent = Intent(this, ResultActivity::class.java).apply {
                    putExtra("EXTRA_MESSAGE", mensagem)
                }
                startActivity(intent)
            }
        }

    }

}