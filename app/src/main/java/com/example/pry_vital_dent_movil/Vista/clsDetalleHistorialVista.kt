package com.example.pry_vital_dent_movil.Vista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pry_vital_dent_movil.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView

class clsDetalleHistorialVista : AppCompatActivity() {

    companion object {
        // El id del historial que tocó el paciente, por ejemplo "Q12" (consulta) o "R5" (receta)
        const val EXTRA_ID_HISTORIAL = "ID_HISTORIAL"
    }

    private lateinit var tbDetalleHistorial: MaterialToolbar
    private lateinit var cvReceta: MaterialCardView
    private lateinit var tvFechaReceta: TextView
    private lateinit var tvFolioReceta: TextView
    private lateinit var tvMedico: TextView
    private lateinit var tvEspecialidad: TextView
    private lateinit var tvDiagnostico: TextView
    private lateinit var tvObservaciones: TextView
    private lateinit var tvSeguimiento: TextView
    private lateinit var llMedicamentos: LinearLayout
    private lateinit var tvIndicaciones: TextView
    private lateinit var tvFirmaDoctor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_historial)

        tbDetalleHistorial = findViewById(R.id.tbDetalleHistorial)
        cvReceta = findViewById(R.id.cvReceta)
        tvFechaReceta = findViewById(R.id.tvFechaReceta)
        tvFolioReceta = findViewById(R.id.tvFolioReceta)
        tvMedico = findViewById(R.id.tvMedico)
        tvEspecialidad = findViewById(R.id.tvEspecialidad)
        tvDiagnostico = findViewById(R.id.tvDiagnostico)
        tvObservaciones = findViewById(R.id.tvObservaciones)
        tvSeguimiento = findViewById(R.id.tvSeguimiento)
        llMedicamentos = findViewById(R.id.llMedicamentos)
        tvIndicaciones = findViewById(R.id.tvIndicaciones)
        tvFirmaDoctor = findViewById(R.id.tvFirmaDoctor)

        // La flecha de arriba regresa al historial
        tbDetalleHistorial.setNavigationOnClickListener { finish() }

        MostrarEjemplo()
    }

    // Por ahora solo es el diseño: se llena con textos de ejemplo.
    // Cuando se conecte detalle_historial.php estos datos llegarán de la API.
    private fun MostrarEjemplo() {
        val fecha = getString(R.string.ej_detalle_fecha)
        tbDetalleHistorial.subtitle = fecha
        tvFechaReceta.text = fecha
        tvFolioReceta.text = getString(R.string.ej_detalle_folio)
        tvFolioReceta.visibility = View.VISIBLE

        tvMedico.text = getString(R.string.ej_detalle_doctor)
        tvEspecialidad.text = getString(R.string.ej_detalle_especialidad)
        tvDiagnostico.text = getString(R.string.ej_detalle_diagnostico)
        tvObservaciones.text = getString(R.string.ej_detalle_observaciones)
        tvSeguimiento.text = getString(R.string.ej_detalle_seguimiento)

        // Un renglón de medicamento de ejemplo (item_medicamento.xml)
        llMedicamentos.removeAllViews()
        val renglon = LayoutInflater.from(this).inflate(R.layout.item_medicamento, llMedicamentos, false)
        renglon.findViewById<TextView>(R.id.tvMedicamentoNombre).text = getString(R.string.ej_detalle_medicamento)
        renglon.findViewById<TextView>(R.id.tvMedicamentoIndicacion).text = getString(R.string.ej_detalle_indicacion)
        llMedicamentos.addView(renglon)

        tvIndicaciones.text = getString(R.string.ej_detalle_indicaciones)
        tvFirmaDoctor.text = getString(R.string.ej_detalle_doctor)
        cvReceta.visibility = View.VISIBLE
    }
}

// Lógica: se abre desde el historial con el id que tocó el paciente y por ahora enseña el diseño de la receta con datos de ejemplo.
