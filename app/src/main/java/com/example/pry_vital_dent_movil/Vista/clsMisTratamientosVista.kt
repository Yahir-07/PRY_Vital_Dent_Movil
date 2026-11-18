package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Adaptador.TratamientoAdaptador
import com.example.pry_vital_dent_movil.Modelo.DataTratamiento
import com.example.pry_vital_dent_movil.Presentador.clsMisTratamientosPresentador
import com.google.android.material.appbar.MaterialToolbar

class clsMisTratamientosVista : AppCompatActivity() {

    private lateinit var presentador: clsMisTratamientosPresentador
    private lateinit var tbTratamientos: MaterialToolbar
    private lateinit var tlTratamientos: TabLayout
    private lateinit var rvTratamientos: RecyclerView
    private lateinit var tvAvisoTratamientos: TextView
    private lateinit var tratamientoAdaptador: TratamientoAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_tratamientos)

        tbTratamientos = findViewById(R.id.tbTratamientos)
        tlTratamientos = findViewById(R.id.tlTratamientos)
        rvTratamientos = findViewById(R.id.rvTratamientos)
        tvAvisoTratamientos = findViewById(R.id.tvAvisoTratamientos)

        // 1. PRIMERO inicializamos el adaptador y lo configuramos en el RecyclerView
        tratamientoAdaptador = TratamientoAdaptador(emptyList()) { tratamientoSeleccionado ->
            presentador.ClickTratamiento(tratamientoSeleccionado)
        }
        rvTratamientos.layoutManager = LinearLayoutManager(this)
        rvTratamientos.adapter = tratamientoAdaptador

        // 2. HASTA DESPUÉS inicializamos el presentador y cargamos los datos
        presentador = clsMisTratamientosPresentador(this)
        presentador.CargarTratamientosEnCurso()

        // Listener para las pestañas (En curso / Finalizados)
        tlTratamientos.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> presentador.CargarTratamientosEnCurso()
                    1 -> presentador.CargarTratamientosFinalizados()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        configurarBarraInferior(2) // Barra inferior (Tratamientos = posición 2), está en NavegacionHelper.kt
    }

    fun MostrarListaTratamientos(lista: List<DataTratamiento>) {
        tratamientoAdaptador.actualizarLista(lista)
    }

    fun MostrarAviso(mensaje: String) {
        tvAvisoTratamientos.text = mensaje
    }

    fun IrADetalleTratamiento(idTratamiento: String) {
        val intent = Intent(this, clsAvanceTratamientoVista::class.java)
        intent.putExtra(clsAvanceTratamientoVista.EXTRA_ID_TRATAMIENTO, idTratamiento)
        startActivity(intent)
    }
}
