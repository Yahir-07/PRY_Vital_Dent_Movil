package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsSesionPorTerminarPresentador

class clsSesionPorTerminarVista : AppCompatActivity() {

    private lateinit var presentador: clsSesionPorTerminarPresentador
    private lateinit var tvSesionPorTerminarMensaje: TextView
    private lateinit var btnSeguirConectado: MaterialButton
    private lateinit var btnCerrarSesionInactividad: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialogo_sesion_por_terminar)

        tvSesionPorTerminarMensaje = findViewById(R.id.tvSesionPorTerminarMensaje)
        btnSeguirConectado = findViewById(R.id.btnSeguirConectado)
        btnCerrarSesionInactividad = findViewById(R.id.btnCerrarSesionInactividad)

        presentador = clsSesionPorTerminarPresentador(this)

        tvSesionPorTerminarMensaje.text = "Por seguridad cerraremos tu sesión en 1:45 por inactividad."

        btnSeguirConectado.setOnClickListener {
            presentador.ClickSeguirConectado()
        }

        btnCerrarSesionInactividad.setOnClickListener {
            presentador.ClickCerrarSesion()
        }
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun RegresarAlFlujo() {
        finish() // Cierra el aviso y regresa a la pantalla anterior
    }

    fun CerrarSesionYIrALogin() {
        val intent = Intent(this, clsLoginVista::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}