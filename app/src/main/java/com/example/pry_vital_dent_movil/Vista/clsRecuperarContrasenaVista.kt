package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsRecuperarContrasenaPresentador

class clsRecuperarContrasenaVista : AppCompatActivity() {

    private lateinit var presentador: clsRecuperarContrasenaPresentador
    private lateinit var etCorreoRecuperacion: TextInputEditText
    private lateinit var btnEnviarEnlace: Button
    private lateinit var tvAvisoEnlaceEnviado: TextView
    private lateinit var btnVolverIniciarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasena)

        // Enlazando con findViewById
        etCorreoRecuperacion = findViewById(R.id.etCorreoRecuperacion)
        btnEnviarEnlace = findViewById(R.id.btnEnviarEnlace)
        tvAvisoEnlaceEnviado = findViewById(R.id.tvAvisoEnlaceEnviado)
        btnVolverIniciarSesion = findViewById(R.id.btnVolverIniciarSesion)

        // Inicializamos el presentador correctamente
        val presentador = clsRecuperarContrasenaPresentador(this)

        // Configuración de clics
        btnEnviarEnlace.setOnClickListener {
            val correo = etCorreoRecuperacion.text.toString().trim()
            presentador.ClickEnviarEnlace(correo)
        }

        btnVolverIniciarSesion.setOnClickListener {
            presentador.ClickVolverLogin()
        }
    }

    // Métodos que ordenan los cambios visuales
    fun MostrarAvisoEnviado(mensaje: String) {
        tvAvisoEnlaceEnviado.text = mensaje
        tvAvisoEnlaceEnviado.visibility = View.VISIBLE
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun VolverAlLogin() {
        val intent = Intent(this, clsLoginVista::class.java)
        startActivity(intent)
        finish()
    }
}