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
import com.example.pry_vital_dent_movil.Presentador.clsLoginPresentador

class clsLoginVista : AppCompatActivity() {

    private lateinit var presentador: clsLoginPresentador
    private lateinit var tvErrorCredenciales: TextView
    private lateinit var etUsuario: TextInputEditText
    private lateinit var etContrasena: TextInputEditText
    private lateinit var btnOlvidasteContrasena: Button
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnIngresarHuella: Button
    private lateinit var btnIrCrearCuenta: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)

        tvErrorCredenciales = findViewById(R.id.tvErrorCredenciales)
        etUsuario = findViewById(R.id.etUsuario)
        etContrasena = findViewById(R.id.etContrasena)
        btnOlvidasteContrasena = findViewById(R.id.btnOlvidasteContrasena)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnIngresarHuella = findViewById(R.id.btnIngresarHuella)
        btnIrCrearCuenta = findViewById(R.id.btnIrCrearCuenta)

        presentador = clsLoginPresentador(this)

        btnIniciarSesion.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()
            presentador.ClickIniciarSesion(usuario, contrasena)
        }

        btnOlvidasteContrasena.setOnClickListener {
            presentador.ClickOlvidasteContrasena()
        }

        btnIngresarHuella.setOnClickListener {
            presentador.ClickIngresarHuella()
        }

        btnIrCrearCuenta.setOnClickListener {
            presentador.ClickIrCrearCuenta()
        }
    }

    // Métodos que ordenan los cambios visuales
    fun MostrarError(mensaje: String) {
        tvErrorCredenciales.text = mensaje
        tvErrorCredenciales.visibility = View.VISIBLE
    }

    fun OcultarError() {
        tvErrorCredenciales.visibility = View.GONE
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun IrAlPanelPrincipal() {
        val intent = Intent(this, clsInicioVista::class.java)
        startActivity(intent)
        finish()
    }

    fun IrARecuperarContrasena() {
        val intent = Intent(this, clsRecuperarContrasenaVista::class.java)
        startActivity(intent)
    }

    fun IrARegistro() {
        val intent = Intent(this, clsRegistroVista::class.java)
        startActivity(intent)
        finish()
    }
}