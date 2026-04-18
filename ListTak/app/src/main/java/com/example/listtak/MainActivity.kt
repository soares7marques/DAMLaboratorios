package com.example.listtak

import android.os.Bundle
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
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

        // --- LÓGICA DA LISTA DE TAREFAS ---
        // 1. Referências dos componentes
        val etTask = findViewById<EditText>(R.id.etTask)
        val lvTasks = findViewById<ListView>(R.id.lvTasks)

        // 2. Criar a lista de Strings e o Adaptador
        val taskList = ArrayList<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, taskList)

        // 3. Vincular o adaptador ao ListView
        lvTasks.adapter = adapter

        // 4. Capturar a tecla Enter no EditText
        etTask.setOnKeyListener { view, keyCode, event ->
            // Verifica se a tecla pressionada foi "Enter" e se a ação foi "pressionar" (DOWN)
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val taskText = etTask.text.toString()

                if (taskText.isNotBlank()) {
                    // Adiciona no topo da lista (índice 0)
                    taskList.add(0, taskText)

                    // Notifica o adaptador que os dados mudaram
                    adapter.notifyDataSetChanged()

                    // Limpa o campo de texto
                    etTask.text.clear()
                }
                true // Evento consumido
            } else {
                false // Passa o evento adiante
            }
        }
    }
}