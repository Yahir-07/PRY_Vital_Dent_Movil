package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pry_vital_dent_movil.Presentador.clsCuestionario1Presentador
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.example.pry_vital_dent_movil.R
class clsCuestionario1Vista : AppCompatActivity() {

    private lateinit var presentador: clsCuestionario1Presentador

    // Declaración de componentes con tus IDs exactos
    private lateinit var etNombre: TextInputEditText
    private lateinit var etApellidoPaterno: TextInputEditText
    private lateinit var etApellidoMaterno: TextInputEditText
    private lateinit var etFechaNacimiento: TextInputEditText
    private lateinit var actvSexo: MaterialAutoCompleteTextView
    private lateinit var etTelefono: TextInputEditText
    private lateinit var etCodigoPostal: TextInputEditText
    private lateinit var etDatosDomicilio: TextInputEditText
    private lateinit var etLocalidad: TextInputEditText
    private lateinit var etCalle: TextInputEditText
    private lateinit var etReferencia: TextInputEditText
    private lateinit var btnContinuar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_personales)


        etNombre = findViewById(R.id.etNombre)
        etApellidoPaterno = findViewById(R.id.etApellidoPaterno)
        etApellidoMaterno = findViewById(R.id.etApellidoMaterno)
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento)
        actvSexo = findViewById(R.id.actvSexo)
        etTelefono = findViewById(R.id.etTelefono)
        etCodigoPostal = findViewById(R.id.etCodigoPostal)
        etDatosDomicilio = findViewById(R.id.etDatosDomicilio)
        etLocalidad = findViewById(R.id.etLocalidad)
        etCalle = findViewById(R.id.etCalle)
        etReferencia = findViewById(R.id.etReferencia)
        btnContinuar = findViewById(R.id.btnContinuar)

        // Inicializamos el presentador
        presentador = clsCuestionario1Presentador(this)

        // Configuración de clics
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

    fun IrAlPasoDos() {
        Toast.makeText(this, "Avanzando al Paso 2...", Toast.LENGTH_SHORT).show()
        // val intent = Intent(this, clsCuestionarioPaso2Vista::class.java)
        // startActivity(intent)
    }
}