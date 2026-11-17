package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsCitaAgendadaPresentador

class clsCitaAgendadaVista : AppCompatActivity() {

    private lateinit var presentador: clsCitaAgendadaPresentador
    private lateinit var tvResumenFecha: TextView
    private lateinit var tvResumenHora: TextView
    private lateinit var tvResumenMotivo: TextView
    private lateinit var tvResumenDoctor: TextView
    private lateinit var tvResumenEstado: TextView
    private lateinit var btnVerMisCitas: Button
    private lateinit var btnVolverInicio: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cita_agendada)


        tvResumenFecha = findViewById(R.id.tvResumenFecha)
        tvResumenHora = findViewById(R.id.tvResumenHora)
        tvResumenMotivo = findViewById(R.id.tvResumenMotivo)
        tvResumenDoctor = findViewById(R.id.tvResumenDoctor)
        tvResumenEstado = findViewById(R.id.tvResumenEstado)
        btnVerMisCitas = findViewById(R.id.btnVerMisCitas)
        btnVolverInicio = findViewById(R.id.btnVolverInicio)

        presentador = clsCitaAgendadaPresentador(this)
        presentador.CargarResumenCita()

        btnVerMisCitas.setOnClickListener {
            presentador.ClickVerMisCitas()
        }

        btnVolverInicio.setOnClickListener {
            presentador.ClickVolverInicio()
        }
    }

    fun MostrarResumen(fecha: String, hora: String, motivo: String, doctor: String, estado: String) {
        tvResumenFecha.text = fecha
        tvResumenHora.text = hora
        tvResumenMotivo.text = motivo
        tvResumenDoctor.text = doctor
        tvResumenEstado.text = estado
    }

    fun IrAMisCitas() {
        val intent = Intent(this, clsMisCitasVista::class.java)
        startActivity(intent)
        finish()
    }

    fun IrAlInicio() {
        val intent = Intent(this, clsInicioVista::class.java)
        startActivity(intent)
        finishAffinity() // Limpia el historial para regresar al panel principal de forma limpia
    }
}