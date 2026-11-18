package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Adaptador.HorasAdaptador
import com.example.pry_vital_dent_movil.Modelo.DataHora
import com.example.pry_vital_dent_movil.Presentador.clsAgendarCitaPresentador
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class clsAgendarCitaVista : AppCompatActivity() {

    private lateinit var presentador: clsAgendarCitaPresentador
    private lateinit var tbAgendarCita: MaterialToolbar
    private lateinit var calCita: CalendarView
    private lateinit var tvHoraDisponible: TextView
    private lateinit var rvHoras: RecyclerView
    private lateinit var etMotivo: TextInputEditText
    private lateinit var btnConfirmarCita: Button
    private lateinit var adaptadorHoras: HorasAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_cita)

        tbAgendarCita = findViewById(R.id.tbAgendarCita)
        calCita = findViewById(R.id.calCita)
        tvHoraDisponible = findViewById(R.id.tvHoraDisponible)
        rvHoras = findViewById(R.id.rvHoras)
        etMotivo = findViewById(R.id.etMotivo)
        btnConfirmarCita = findViewById(R.id.btnConfirmarCita)

        // No se pueden elegir días pasados
        calCita.minDate = System.currentTimeMillis() - 1000

        presentador = clsAgendarCitaPresentador(this)

        // Casillas de horario en 4 columnas (el GridLayoutManager viene del XML)
        adaptadorHoras = HorasAdaptador(emptyList()) { horaElegida ->
            presentador.SeleccionarHora(horaElegida)
        }
        rvHoras.adapter = adaptadorHoras

        // Obtener la fecha actual por defecto al abrir
        val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(calCita.date))
        presentador.SeleccionarFecha(fechaActual)

        // Listener cuando el usuario cambia de fecha en el CalendarView
        calCita.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val fechaFormateada = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year)
            presentador.SeleccionarFecha(fechaFormateada)
        }

        // Clic en el botón para confirmar la cita
        btnConfirmarCita.setOnClickListener {
            val motivo = etMotivo.text.toString().trim()
            presentador.ClickConfirmarCita(motivo)
        }
    }

    fun MostrarHorariosDisponibles(fecha: String, listaHoras: List<DataHora>) {
        tvHoraDisponible.text = getString(R.string.hora_disponible_fecha, fecha)
        adaptadorHoras.actualizarLista(listaHoras)
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun IrACofirmacionCita() {
        // Redirigir a la pantalla de confirmación o regresar al listado de citas
        val intent = Intent(this, clsMisCitasVista::class.java)
        startActivity(intent)
        finish()
    }
}