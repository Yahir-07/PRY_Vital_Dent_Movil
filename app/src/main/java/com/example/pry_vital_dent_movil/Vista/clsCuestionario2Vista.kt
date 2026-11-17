package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsCuestionario2Presentador

class clsCuestionario2Vista : AppCompatActivity() {

    private lateinit var presentador: clsCuestionario2Presentador
    private lateinit var actvTipoSangre: MaterialAutoCompleteTextView
    private lateinit var rgTieneAlergia: RadioGroup
    private lateinit var etAlergia: TextInputEditText
    private lateinit var cbOrtodonciaPrevia: CheckBox
    private lateinit var cbExtracciones: CheckBox
    private lateinit var cbEndodoncia: CheckBox
    private lateinit var cbSangradoEncias: CheckBox
    private lateinit var cbBruxismo: CheckBox
    private lateinit var cbNinguno: CheckBox
    private lateinit var etEnfermedades: TextInputEditText
    private lateinit var etMedicamentos: TextInputEditText
    private lateinit var etMotivoConsulta: TextInputEditText
    private lateinit var btnAtras: Button
    private lateinit var btnContinuar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_clinicos)

        // Enlazando con findViewById
        actvTipoSangre = findViewById(R.id.actvTipoSangre)
        rgTieneAlergia = findViewById(R.id.rgTieneAlergia)
        etAlergia = findViewById(R.id.etAlergia)
        cbOrtodonciaPrevia = findViewById(R.id.cbOrtodonciaPrevia)
        cbExtracciones = findViewById(R.id.cbExtracciones)
        cbEndodoncia = findViewById(R.id.cbEndodoncia)
        cbSangradoEncias = findViewById(R.id.cbSangradoEncias)
        cbBruxismo = findViewById(R.id.cbBruxismo)
        cbNinguno = findViewById(R.id.cbNinguno)
        etEnfermedades = findViewById(R.id.etEnfermedades)
        etMedicamentos = findViewById(R.id.etMedicamentos)
        etMotivoConsulta = findViewById(R.id.etMotivoConsulta)
        btnAtras = findViewById(R.id.btnAtras)
        btnContinuar = findViewById(R.id.btnContinuar)

        // Inicializamos el presentador
        presentador = clsCuestionario2Presentador(this)

        // Configuración de clics
        btnContinuar.setOnClickListener {
            val tipoSangre = actvTipoSangre.text.toString().trim()
            val tieneAlergia = rgTieneAlergia.checkedRadioButtonId == R.id.rbAlergiaSi
            val alergiaDetalle = etAlergia.text.toString().trim()

            val ortodoncia = cbOrtodonciaPrevia.isChecked
            val extracciones = cbExtracciones.isChecked
            val endodoncia = cbEndodoncia.isChecked
            val sangrado = cbSangradoEncias.isChecked
            val bruxismo = cbBruxismo.isChecked
            val ninguno = cbNinguno.isChecked

            val enfermedades = etEnfermedades.text.toString().trim()
            val medicamentos = etMedicamentos.text.toString().trim()
            val motivo = etMotivoConsulta.text.toString().trim()

            presentador.ClickContinuar(
                tipoSangre, tieneAlergia, alergiaDetalle,
                ortodoncia, extracciones, endodoncia,
                sangrado, bruxismo, ninguno,
                enfermedades, medicamentos, motivo
            )
        }

        btnAtras.setOnClickListener {
            presentador.ClickAtras()
        }
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun IrAlPasoTres() {
        Toast.makeText(this, "Avanzando al Paso 3...", Toast.LENGTH_SHORT).show()
        // val intent = Intent(this, clsCuestionarioPaso3Vista::class.java)
        // startActivity(intent)
    }

    fun RegresarAlPasoUno() {
        finish() // Regresa al paso anterior
    }
}