package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.pry_vital_dent_movil.Presentador.clsCuestionario1Presentador
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.example.pry_vital_dent_movil.R

class clsCuestionario1Vista : AppCompatActivity() {

    private lateinit var presentador: clsCuestionario1Presentador
    private lateinit var etNombre: TextInputEditText
    private lateinit var etApellidoPaterno: TextInputEditText
    private lateinit var etApellidoMaterno: TextInputEditText
    private lateinit var etFechaNacimiento: TextInputEditText
    private lateinit var actvSexo: MaterialAutoCompleteTextView
    private lateinit var etTelefono: TextInputEditText
    private lateinit var tilCodigoPostal: TextInputLayout
    private lateinit var etCodigoPostal: TextInputEditText
    private lateinit var etDatosDomicilio: TextInputEditText
    private lateinit var etLocalidad: MaterialAutoCompleteTextView
    private lateinit var etCalle: TextInputEditText
    private lateinit var etReferencia: TextInputEditText
    private lateinit var btnContinuar: Button
    private lateinit var camposObligatorios: Map<String, TextInputLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_personales)

        etNombre = findViewById(R.id.etNombre)
        etApellidoPaterno = findViewById(R.id.etApellidoPaterno)
        etApellidoMaterno = findViewById(R.id.etApellidoMaterno)
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento)
        actvSexo = findViewById(R.id.actvSexo)
        etTelefono = findViewById(R.id.etTelefono)
        tilCodigoPostal = findViewById(R.id.tilCodigoPostal)
        etCodigoPostal = findViewById(R.id.etCodigoPostal)
        etDatosDomicilio = findViewById(R.id.etDatosDomicilio)
        etLocalidad = findViewById(R.id.etLocalidad)
        etCalle = findViewById(R.id.etCalle)
        etReferencia = findViewById(R.id.etReferencia)
        btnContinuar = findViewById(R.id.btnContinuar)

        etFechaNacimiento.setOnClickListener { MostrarCalendario() }

        camposObligatorios = mapOf(
            "nombre" to findViewById<TextInputLayout>(R.id.tilNombre),
            "apPaterno" to findViewById<TextInputLayout>(R.id.tilApellidoPaterno),
            "fechaNac" to findViewById<TextInputLayout>(R.id.tilFechaNacimiento),
            "sexo" to findViewById<TextInputLayout>(R.id.tilSexo),
            "telefono" to findViewById<TextInputLayout>(R.id.tilTelefono)
        )

        intent.getStringExtra(clsRegistroVista.EXTRA_TELEFONO)?.let { etTelefono.setText(it) }

        presentador = clsCuestionario1Presentador(this)

        // Cada que cambia el CP se le avisa al presentador, él decide cuándo buscar
        etCodigoPostal.doAfterTextChanged { texto ->
            presentador.CodigoPostalCambio(texto.toString().trim())
        }

        // Para que la lista de colonias se abra con solo tocar
        etLocalidad.setOnClickListener {
            etLocalidad.showDropDown()
        }

        btnContinuar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apPaterno = etApellidoPaterno.text.toString().trim()
            val apMaterno = etApellidoMaterno.text.toString().trim()
            val fechaNac = etFechaNacimiento.text.toString().trim()
            val sexo = actvSexo.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val cp = etCodigoPostal.text.toString().trim()
            val domicilio = etDatosDomicilio.text.toString().trim()
            val localidad = etLocalidad.text.toString().trim()
            val calle = etCalle.text.toString().trim()
            val referencia = etReferencia.text.toString().trim()

            presentador.ClickContinuar(
                nombre, apPaterno, apMaterno, fechaNac, sexo,
                telefono, cp, domicilio, localidad, calle, referencia
            )
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

    // ---------- Código postal (SEPOMEX) ----------

    fun CodigoPostalEscrito(): String = etCodigoPostal.text.toString().trim()

    fun MostrarBuscandoCodigoPostal(buscando: Boolean) {
        tilCodigoPostal.error = null
        tilCodigoPostal.helperText = if (buscando) "Buscando tu código postal…" else null
    }

    // Pone el municipio y estado, y carga las colonias en la lista. Si solo hay una, la elige sola.
    fun MostrarDatosCodigoPostal(datosDomicilio: String, colonias: List<String>) {
        etDatosDomicilio.setText(datosDomicilio)
        etLocalidad.setSimpleItems(colonias.toTypedArray())

        if (colonias.size == 1) {
            etLocalidad.setText(colonias[0], false)
        }
    }

    // Borra lo que se llenó solo, para que no se quede el municipio de otro CP
    fun LimpiarDatosDomicilio() {
        etDatosDomicilio.setText("")
        etLocalidad.setText("", false)
        etLocalidad.setSimpleItems(arrayOf())
    }

    fun MostrarErrorCodigoPostal(mensaje: String) {
        tilCodigoPostal.error = mensaje
    }

    fun IrAlPasoDos() {
        startActivity(Intent(this, clsCuestionario2Vista::class.java))
    }
    private fun MostrarCalendario() {
        val hoy = java.util.Calendar.getInstance()
        val dialogo = android.app.DatePickerDialog(this, { _, anio, mes, dia ->
            etFechaNacimiento.setText(String.format(java.util.Locale.US, "%02d/%02d/%04d", dia, mes + 1, anio))
            camposObligatorios["fechaNac"]?.error = null
        }, hoy.get(java.util.Calendar.YEAR) - 18, hoy.get(java.util.Calendar.MONTH), hoy.get(java.util.Calendar.DAY_OF_MONTH))
        dialogo.datePicker.maxDate = hoy.timeInMillis
        dialogo.show()
    }
}