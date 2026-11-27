package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsRegistroPresentador
import com.google.android.material.appbar.MaterialToolbar

class clsRegistroVista : AppCompatActivity() {

    private lateinit var presentador: clsRegistroPresentador
    private lateinit var etNombreUsuario: TextInputEditText
    private lateinit var etCorreo: TextInputEditText
    private lateinit var etTelefono: TextInputEditText
    private lateinit var etContrasena: TextInputEditText
    private lateinit var etConfirmarContrasena: TextInputEditText
    private lateinit var cbAvisoPrivacidad: CheckBox
    private lateinit var btnCrearCuenta: Button
    private lateinit var btnIrIniciarSesion: Button
    private lateinit var camposObligatorios: Map<String, TextInputLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        findViewById<MaterialToolbar>(R.id.tbCrearCuenta).mostrarLogo()
        etNombreUsuario = findViewById(R.id.etNombreUsuario)
        etCorreo = findViewById(R.id.etCorreo)
        etTelefono = findViewById(R.id.etTelefono)
        etContrasena = findViewById(R.id.etContrasena)
        etConfirmarContrasena = findViewById(R.id.etConfirmarContrasena)
        cbAvisoPrivacidad = findViewById(R.id.cbAvisoPrivacidad)
        cbAvisoPrivacidad.conectarAvisoPrivacidad(this) // abre la burbuja del aviso de privacidad
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        btnIrIniciarSesion = findViewById(R.id.btnIrIniciarSesion)

        camposObligatorios = mapOf(
            "nombre" to findViewById<TextInputLayout>(R.id.tilNombreUsuario),
            "correo" to findViewById<TextInputLayout>(R.id.tilCorreo),
            "telefono" to findViewById<TextInputLayout>(R.id.tilTelefono),
            "contrasena" to findViewById<TextInputLayout>(R.id.tilContrasena),
            "confirmar" to findViewById<TextInputLayout>(R.id.tilConfirmarContrasena)
        )
        camposObligatorios.values.forEach { it.errorIconDrawable = null }

        presentador = clsRegistroPresentador(this)

        btnCrearCuenta.setOnClickListener {
            val nombre = etNombreUsuario.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()
            val confirmar = etConfirmarContrasena.text.toString().trim()
            val aceptaAviso = cbAvisoPrivacidad.isChecked

            presentador.ClickCrearCuenta(nombre, correo, telefono, contrasena, confirmar, aceptaAviso)
        }

        btnIrIniciarSesion.setOnClickListener {
            presentador.ClickIrLogin()
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

    fun HabilitarBoton(habilitado: Boolean) {
        btnCrearCuenta.isEnabled = habilitado
    }

    fun IrAlCuestionario(nombre: String, telefono: String) {
        val intent = Intent(this, clsCuestionario1Vista::class.java)
        intent.putExtra(EXTRA_NOMBRE, nombre)
        intent.putExtra(EXTRA_TELEFONO, telefono)
        startActivity(intent)
        finish()
    }

    fun IrAlLogin() {
        val intent = Intent(this, clsLoginVista::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_TELEFONO = "telefono"
        const val EXTRA_NOMBRE = "nombre"
    }
}