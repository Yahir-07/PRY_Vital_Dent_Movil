package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsRecuperarContrasenaPresentador
import com.google.android.material.appbar.MaterialToolbar

class clsRecuperarContrasenaVista : AppCompatActivity() {

    private lateinit var presentador: clsRecuperarContrasenaPresentador
    private lateinit var etCorreoRecuperacion: TextInputEditText
    private lateinit var btnEnviarEnlace: Button
    private lateinit var tvAvisoEnlaceEnviado: TextView
    private lateinit var btnVolverIniciarSesion: Button
    private lateinit var llPasoCodigo: LinearLayout
    private lateinit var etCodigo: TextInputEditText
    private lateinit var etNuevaContrasena: TextInputEditText
    private lateinit var etConfirmarNuevaContrasena: TextInputEditText
    private lateinit var btnCambiarContrasena: Button
    private lateinit var btnReenviarCodigo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasena)

        findViewById<MaterialToolbar>(R.id.tbRecuperarContrasena).mostrarLogo()
        etCorreoRecuperacion = findViewById(R.id.etCorreoRecuperacion)
        btnEnviarEnlace = findViewById(R.id.btnEnviarEnlace)
        tvAvisoEnlaceEnviado = findViewById(R.id.tvAvisoEnlaceEnviado)
        btnVolverIniciarSesion = findViewById(R.id.btnVolverIniciarSesion)
        llPasoCodigo = findViewById(R.id.llPasoCodigo)
        etCodigo = findViewById(R.id.etCodigo)
        etNuevaContrasena = findViewById(R.id.etNuevaContrasena)
        etConfirmarNuevaContrasena = findViewById(R.id.etConfirmarNuevaContrasena)
        btnCambiarContrasena = findViewById(R.id.btnCambiarContrasena)
        btnReenviarCodigo = findViewById(R.id.btnReenviarCodigo)

        presentador = clsRecuperarContrasenaPresentador(this)

        btnEnviarEnlace.setOnClickListener {
            presentador.ClickEnviarCodigo(etCorreoRecuperacion.text.toString().trim())
        }

        btnReenviarCodigo.setOnClickListener {
            presentador.ClickReenviarCodigo()
        }

        btnCambiarContrasena.setOnClickListener {
            presentador.ClickCambiarContrasena(
                etCodigo.text.toString().trim(),
                etNuevaContrasena.text.toString(),
                etConfirmarNuevaContrasena.text.toString()
            )
        }

        btnVolverIniciarSesion.setOnClickListener {
            presentador.ClickVolverLogin()
        }
    }

    fun MostrarAvisoEnviado(mensaje: String) {
        tvAvisoEnlaceEnviado.text = mensaje
        tvAvisoEnlaceEnviado.visibility = View.VISIBLE
    }

    fun MostrarPasoCodigo() {
        llPasoCodigo.visibility = View.VISIBLE
        etCorreoRecuperacion.isEnabled = false
        btnEnviarEnlace.visibility = View.GONE
        etCodigo.requestFocus()
    }

    fun MostrarProcesando(procesando: Boolean) {
        btnEnviarEnlace.isEnabled = !procesando
        btnCambiarContrasena.isEnabled = !procesando
        btnReenviarCodigo.isEnabled = !procesando
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun VolverAlLogin() {
        val intent = Intent(this, clsLoginVista::class.java)
        startActivity(intent)
        finish()
    }
}
