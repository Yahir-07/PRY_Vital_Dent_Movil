package com.example.pry_vital_dent_movil.Vista

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.pry_vital_dent_movil.Presentador.clsEditarPerfilPresentador
import com.example.pry_vital_dent_movil.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class clsEditarPerfilVista : AppCompatActivity() {

    private lateinit var presentador: clsEditarPerfilPresentador
    private lateinit var tbEditarPerfil: MaterialToolbar
    private lateinit var tilTelefono: TextInputLayout
    private lateinit var etTelefono: TextInputEditText
    private lateinit var tilCorreo: TextInputLayout
    private lateinit var etCorreo: TextInputEditText
    private lateinit var tvDomicilioActual: TextView
    private lateinit var tilCodigoPostal: TextInputLayout
    private lateinit var etCodigoPostal: TextInputEditText
    private lateinit var etMunicipio: TextInputEditText
    private lateinit var etEstado: TextInputEditText
    private lateinit var tilColonia: TextInputLayout
    private lateinit var etColonia: MaterialAutoCompleteTextView
    private lateinit var tilCalle: TextInputLayout
    private lateinit var etCalle: TextInputEditText
    private lateinit var btnCancelar: MaterialButton
    private lateinit var btnGuardarCambios: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        tbEditarPerfil = findViewById(R.id.tbEditarPerfil)
        tilTelefono = findViewById(R.id.tilTelefono)
        etTelefono = findViewById(R.id.etTelefono)
        tilCorreo = findViewById(R.id.tilCorreo)
        etCorreo = findViewById(R.id.etCorreo)
        tvDomicilioActual = findViewById(R.id.tvDomicilioActual)
        tilCodigoPostal = findViewById(R.id.tilCodigoPostal)
        etCodigoPostal = findViewById(R.id.etCodigoPostal)
        etMunicipio = findViewById(R.id.etMunicipio)
        etEstado = findViewById(R.id.etEstado)
        tilColonia = findViewById(R.id.tilColonia)
        etColonia = findViewById(R.id.etColonia)
        tilCalle = findViewById(R.id.tilCalle)
        etCalle = findViewById(R.id.etCalle)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios)

        tbEditarPerfil.mostrarLogo()

        presentador = clsEditarPerfilPresentador(this)

        // Datos que manda Mi perfil
        val telefono = intent.getStringExtra("telefono") ?: ""
        val correo = intent.getStringExtra("correo") ?: ""
        val domicilio = intent.getStringExtra("domicilio") ?: ""
        presentador.MostrarDatosActuales(telefono, correo, domicilio)

        // doAfterTextChanged se ejecuta cada que cambia el texto
        etCodigoPostal.doAfterTextChanged { texto ->
            presentador.CodigoPostalCambio(texto.toString().trim())
        }

        // Para que la lista se abra con solo tocar
        etColonia.setOnClickListener {
            etColonia.showDropDown()
        }

        btnGuardarCambios.setOnClickListener {
            val telefonoNuevo = etTelefono.text.toString().trim()
            val correoNuevo = etCorreo.text.toString().trim()
            val cp = etCodigoPostal.text.toString().trim()
            val colonia = etColonia.text.toString().trim()
            val calle = etCalle.text.toString().trim()
            presentador.ClickGuardarCambios(telefonoNuevo, correoNuevo, cp, colonia, calle)
        }

        btnCancelar.setOnClickListener {
            presentador.ClickCancelar()
        }
    }

    fun MostrarDatosActuales(telefono: String, correo: String, domicilio: String) {
        etTelefono.setText(telefono)
        etCorreo.setText(correo)
        tvDomicilioActual.text = domicilio.ifEmpty { "Sin domicilio registrado" }
    }

    fun CodigoPostalEscrito(): String = etCodigoPostal.text.toString().trim()

    // ---------- Código postal (SEPOMEX) ----------

    fun MostrarBuscandoCodigoPostal(buscando: Boolean) {
        tilCodigoPostal.helperText = if (buscando) "Buscando tu código postal…" else null
    }

    fun MostrarDatosCodigoPostal(municipio: String, estado: String, colonias: List<String>) {
        tilCodigoPostal.error = null
        etMunicipio.setText(municipio)
        etEstado.setText(estado)
        etColonia.setSimpleItems(colonias.toTypedArray())

        // Si solo hay una colonia, se elige sola
        if (colonias.size == 1) {
            etColonia.setText(colonias[0], false)
        }
    }

    fun LimpiarDatosDomicilio() {
        etMunicipio.setText("")
        etEstado.setText("")
        etColonia.setText("", false)
        etColonia.setSimpleItems(arrayOf())
    }

    // ---------- Errores en los campos ----------

    fun LimpiarErrores() {
        tilTelefono.error = null
        tilCorreo.error = null
        tilCodigoPostal.error = null
        tilColonia.error = null
        tilCalle.error = null
    }

    fun MostrarErrorTelefono(mensaje: String) {
        tilTelefono.error = mensaje
        etTelefono.requestFocus()
    }

    fun MostrarErrorCorreo(mensaje: String) {
        tilCorreo.error = mensaje
        etCorreo.requestFocus()
    }

    fun MostrarErrorCodigoPostal(mensaje: String) {
        tilCodigoPostal.error = mensaje
        etCodigoPostal.requestFocus()
    }

    fun MostrarErrorColonia(mensaje: String) {
        tilColonia.error = mensaje
        etColonia.requestFocus()
    }

    fun MostrarErrorCalle(mensaje: String) {
        tilCalle.error = mensaje
        etCalle.requestFocus()
    }

    // ---------- Guardar ----------

    fun MostrarGuardando(guardando: Boolean) {
        btnGuardarCambios.isEnabled = !guardando
        btnCancelar.isEnabled = !guardando
        btnGuardarCambios.text = if (guardando) "Guardando…" else getString(R.string.accion_guardar_cambios)
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    // No se puede cerrar tocando afuera, así siempre regresa a Mi perfil con Aceptar
    fun MostrarDatosGuardados(mensaje: String) {
        MaterialAlertDialogBuilder(this)
            .setMessage(mensaje)
            .setCancelable(false)
            .setPositiveButton(R.string.accion_aceptar) { _, _ -> presentador.ClickAceptarGuardado() }
            .show()
    }

    fun RegresarAlPerfil() {
        finish()
    }
}

// Lógica: muestra los datos actuales del paciente, le avisa al presentador cuando escribe el CP o
// toca Guardar, y enseña los errores o las colonias que le regresan.
