package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsRegistroPresentador
import kotlin.jvm.java

class clsRegistroVista : AppCompatActivity() {

    private lateinit var presentador: clsRegistroPresentador
    private lateinit var etNombreUsuario: TextInputEditText
    private lateinit var etCorreo: TextInputEditText
    private lateinit var etContrasena: TextInputEditText
    private lateinit var etConfirmarContrasena: TextInputEditText
    private lateinit var cbAvisoPrivacidad: CheckBox
    private lateinit var btnCrearCuenta: Button
    private lateinit var btnIrIniciarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        etNombreUsuario = findViewById(R.id.etNombreUsuario)
        etCorreo = findViewById(R.id.etCorreo)
        etContrasena = findViewById(R.id.etContrasena)
        etConfirmarContrasena = findViewById(R.id.etConfirmarContrasena)
        cbAvisoPrivacidad = findViewById(R.id.cbAvisoPrivacidad)
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        btnIrIniciarSesion = findViewById(R.id.btnIrIniciarSesion)

        presentador = clsRegistroPresentador(this)

        btnCrearCuenta.setOnClickListener {
            val nombre = etNombreUsuario.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()
            val confirmar = etConfirmarContrasena.text.toString().trim()
            val aceptaAviso = cbAvisoPrivacidad.isChecked

            presentador.ClickCrearCuenta(nombre, correo, contrasena, confirmar, aceptaAviso)
        }

        btnIrIniciarSesion.setOnClickListener {
            presentador.ClickIrLogin()
        }
    }

    // Funciones que la actividad ejecuta
    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun IrAlLogin() {
        val intent = Intent(this, clsLoginVista::class.java) // Pantalla 03 (Iniciar sesión)
        startActivity(intent)
        finish()
    }
}