package com.example.pry_vital_dent_movil.Vista

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var chipFiltroTodo: Chip
    private lateinit var chipFiltroConsultas: Chip
    private lateinit var chipFiltroRecetas: Chip
    private lateinit var chipFiltroTratamientos: Chip
    private lateinit var rvHistorial: RecyclerView
    private lateinit var historialAdaptador: HistorialAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_historial_medico)

        tbHistorialMedico = findViewById(R.id.tbHistorialMedico)
        tvAvisoSoloLectura = findViewById(R.id.tvAvisoSoloLectura)
        cgFiltrosHistorial = findViewById(R.id.cgFiltrosHistorial)
        chipFiltroTodo = findViewById(R.id.chipFiltroTodo)
        chipFiltroConsultas = findViewById(R.id.chipFiltroConsultas)
        chipFiltroRecetas = findViewById(R.id.chipFiltroRecetas)
        chipFiltroTratamientos = findViewById(R.id.chipFiltroTratamientos)
        rvHistorial = findViewById(R.id.rvHistorial)

        // 1. PRIMERO inicializamos el adaptador y lo asignamos al RecyclerView
        historialAdaptador = HistorialAdaptador(emptyList()) { itemSeleccionado ->
            presentador.SeleccionarHistorial(itemSeleccionado)
        }
        rvHistorial.layoutManager = LinearLayoutManager(this)
        rvHistorial.adapter = historialAdaptador

        // 2. HASTA DESPUÉS inicializamos el presentador y cargamos los datos
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
        // Aquí puedes configurar los chips de alergias si lo requieres
    }

    fun MostrarListaHistorial(lista: List<DataHistorial>) {
        historialAdaptador.actualizarLista(lista)
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}