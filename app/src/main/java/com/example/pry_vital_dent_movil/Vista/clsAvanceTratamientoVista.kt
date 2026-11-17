package com.example.pry_vital_dent_movil.Vista

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsAvanceTratamientoPresentador
import com.google.android.material.appbar.MaterialToolbar

class clsAvanceTratamientoVista : AppCompatActivity() {

    private lateinit var presentador: clsAvanceTratamientoPresentador
    private lateinit var tbDetalleTratamiento: MaterialToolbar
    private lateinit var tvPorcentajeAvance: TextView
    private lateinit var tvSesionesAvance: TextView
    private lateinit var tvFinEstimado: TextView
    private lateinit var tvEstadoTratamiento: TextView
    private lateinit var rvAvances: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detalle_tratamiento)
        tbDetalleTratamiento = findViewById(R.id.tbDetalleTratamiento)
        tvPorcentajeAvance = findViewById(R.id.tvPorcentajeAvance)
        tvSesionesAvance = findViewById(R.id.tvSesionesAvance)
        tvFinEstimado = findViewById(R.id.tvFinEstimado)
        tvEstadoTratamiento = findViewById(R.id.tvEstadoTratamiento)
        rvAvances = findViewById(R.id.rvAvances)

        presentador = clsAvanceTratamientoPresentador(this)
        presentador.CargarDatosDetalle()
    }

    fun MostrarResumen(porcentaje: String, sesiones: String, finEstimado: String, estado: String) {
        tvPorcentajeAvance.text = porcentaje
        tvSesionesAvance.text = sesiones
        tvFinEstimado.text = finEstimado
        tvEstadoTratamiento.text = estado
    }

    fun MostrarListaAvances(listaAvances: List<String>) {
        // Aquí conectarás tu Adapter para el RecyclerView de los avances sesión por sesión
        Toast.makeText(this, "Se cargaron ${listaAvances.size} registros de avance", Toast.LENGTH_SHORT).show()
    }
}