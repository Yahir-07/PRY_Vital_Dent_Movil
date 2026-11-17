package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsSesionVencidaPresentador

class clsSesionVencidaVista : AppCompatActivity() {

    private lateinit var presentador: clsSesionVencidaPresentador
    private lateinit var btnSesionIniciar: MaterialButton
    private lateinit var btnSesionHuella: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialogo_sesion_expirada)

        btnSesionIniciar = findViewById(R.id.btnSesionIniciar)
        btnSesionHuella = findViewById(R.id.btnSesionHuella)

        presentador = clsSesionVencidaPresentador(this)

        btnSesionIniciar.setOnClickListener {
            presentador.ClickIniciarSesion()
        }

        btnSesionHuella.setOnClickListener {
            presentador.ClickUsarHuella()
        }
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun IrALogin() {
        val intent = Intent(this, clsLoginVista::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    fun IrAInicio() {
        val intent = Intent(this, clsInicioVista::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}