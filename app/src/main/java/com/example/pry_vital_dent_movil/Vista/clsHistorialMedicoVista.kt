package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Adaptador.HistorialAdaptador
import com.example.pry_vital_dent_movil.Modelo.DataHistorial
import com.example.pry_vital_dent_movil.Presentador.clsHistorialMedicoPresentador
import com.google.android.material.appbar.MaterialToolbar

class clsHistorialMedicoVista : AppCompatActivity() {

    private lateinit var presentador: clsHistorialMedicoPresentador
    private lateinit var tbHistorialMedico: MaterialToolbar
    private lateinit var tvAvisoSoloLectura: TextView
    private lateinit var cgFiltrosHistorial: ChipGroup
    private lateinit var cgAlergias: ChipGroup
    private lateinit var chipFiltroTodo: Chip
    private lateinit var chipFiltroConsultas: Chip
    private lateinit var chipFiltroRecetas: Chip
    private lateinit var chipFiltroTratamientos: Chip
    private lateinit var rvHistorial: RecyclerView
    private lateinit var tvAviso: TextView
    private lateinit var historialAdaptador: HistorialAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_historial_medico)

        tbHistorialMedico = findViewById(R.id.tbHistorialMedico)
        tvAvisoSoloLectura = findViewById(R.id.tvAvisoSoloLectura)
        cgFiltrosHistorial = findViewById(R.id.cgFiltrosHistorial)
        cgAlergias = findViewById(R.id.cgAlergias)
        chipFiltroTodo = findViewById(R.id.chipFiltroTodo)
        chipFiltroConsultas = findViewById(R.id.chipFiltroConsultas)
        chipFiltroRecetas = findViewById(R.id.chipFiltroRecetas)
        chipFiltroTratamientos = findViewById(R.id.chipFiltroTratamientos)
        rvHistorial = findViewById(R.id.rvHistorial)
        tvAviso = findViewById(R.id.tvAviso)

        tbHistorialMedico.mostrarLogo()

        historialAdaptador = HistorialAdaptador(emptyList()) { itemSeleccionado ->
            presentador.SeleccionarHistorial(itemSeleccionado)
        }
        rvHistorial.layoutManager = LinearLayoutManager(this)
        rvHistorial.adapter = historialAdaptador

        presentador = clsHistorialMedicoPresentador(this)
        presentador.CargarDatosIniciales()

        // Configuración de los filtros por ChipGroup
        cgFiltrosHistorial.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipFiltroTodo -> presentador.FiltrarHistorial("Todo")
                R.id.chipFiltroConsultas -> presentador.FiltrarHistorial("Consultas")
                R.id.chipFiltroRecetas -> presentador.FiltrarHistorial("Recetas")
                R.id.chipFiltroTratamientos -> presentador.FiltrarHistorial("Tratamientos")
            }
        }
    }

    fun MostrarAlergias(alergias: List<String>) {
        cgAlergias.removeAllViews()
        val textos = if (alergias.isEmpty()) listOf("Sin alergias registradas") else alergias
        for (texto in textos) {
            val chip = Chip(this)
            chip.text = texto
            chip.isClickable = false
            cgAlergias.addView(chip)
        }
    }

    fun MostrarListaHistorial(lista: List<DataHistorial>) {
        historialAdaptador.actualizarLista(lista)
    }

    // Abre la pantalla 12b con el id que tocó el paciente (Q12, R5...)
    fun IrADetalleHistorial(idHistorial: String) {
        val intent = Intent(this, clsDetalleHistorialVista::class.java)
        intent.putExtra(clsDetalleHistorialVista.EXTRA_ID_HISTORIAL, idHistorial)
        startActivity(intent)
    }

    // ---------- Aviso de abajo ----------

    fun MostrarAviso(mensaje: String) {
        tvAviso.text = mensaje
        tvAviso.setBackgroundResource(R.drawable.bg_aviso)
        tvAviso.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_punto_info, 0, 0, 0)
        tvAviso.visibility = View.VISIBLE
    }

    // El mismo aviso pero en rojo, para cuando algo falla al cargar
    fun MostrarAvisoError(mensaje: String) {
        tvAviso.text = mensaje
        tvAviso.setBackgroundResource(R.drawable.bg_aviso_error)
        tvAviso.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_punto_error, 0, 0, 0)
        tvAviso.visibility = View.VISIBLE
    }

    fun OcultarAviso() {
        tvAviso.visibility = View.GONE
    }
}