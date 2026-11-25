package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsCuestionario3Presentador

class clsCuestionario3Vista : AppCompatActivity() {

    private lateinit var presentador: clsCuestionario3Presentador
    private lateinit var etNombreContacto: TextInputEditText
    private lateinit var actvParentesco: MaterialAutoCompleteTextView
    private lateinit var etTelefonoContacto: TextInputEditText
    private lateinit var etTelefonoAlterno: TextInputEditText
    private lateinit var cbAvisoPrivacidad: CheckBox
    private lateinit var btnAtras: Button
    private lateinit var btnContinuar: Button
    private lateinit var camposObligatorios: Map<String, TextInputLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacto_emergencia)

        etNombreContacto = findViewById(R.id.etNombreContacto)
        actvParentesco = findViewById(R.id.actvParentesco)
        etTelefonoContacto = findViewById(R.id.etTelefonoContacto)
        etTelefonoAlterno = findViewById(R.id.etTelefonoAlterno)
        cbAvisoPrivacidad = findViewById(R.id.cbAvisoPrivacidad)
        cbAvisoPrivacidad.conectarAvisoPrivacidad(this) // abre la burbuja del aviso de privacidad
        btnAtras = findViewById(R.id.btnAtras)
        btnContinuar = findViewById(R.id.btnContinuar)

        camposObligatorios = mapOf(
            "nombre" to findViewById<TextInputLayout>(R.id.tilNombreContacto),
            "parentesco" to findViewById<TextInputLayout>(R.id.tilParentesco),
            "telefono" to findViewById<TextInputLayout>(R.id.tilTelefonoContacto),
            "telefonoAlterno" to findViewById<TextInputLayout>(R.id.tilTelefonoAlterno)
        )

        presentador = clsCuestionario3Presentador(this)

        btnContinuar.setOnClickListener {
            val nombreContacto = etNombreContacto.text.toString().trim()
            val parentesco = actvParentesco.text.toString().trim()
            val telefonoContacto = etTelefonoContacto.text.toString().trim()
            val telefonoAlterno = etTelefonoAlterno.text.toString().trim()
            val aceptaAviso = cbAvisoPrivacidad.isChecked

            presentador.ClickFinalizar(nombreContacto, parentesco, telefonoContacto, telefonoAlterno, aceptaAviso)
        }

        btnAtras.setOnClickListener {
            presentador.ClickAtras()
        }
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun MostrarErrorCampo(campo: String, mensaje: String) {
        camposObligatorios[campo]?.error = mensaje
    }

    fun LimpiarErrores() {
        camposObligatorios.values.forEach { it.error = null }
    }

    fun IrAlLogin() {
        val intent = Intent(this, clsLoginVista::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun VolverAlPasoDos() {
        finish()
    }
}
