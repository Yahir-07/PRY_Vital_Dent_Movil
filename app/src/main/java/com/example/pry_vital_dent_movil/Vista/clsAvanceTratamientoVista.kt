package com.example.pry_vital_dent_movil.Vista

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Adaptador.AvanceAdaptador
import com.example.pry_vital_dent_movil.Modelo.DataAvance
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
    private lateinit var avanceAdaptador: AvanceAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detalle_tratamiento)

        tbDetalleTratamiento = findViewById(R.id.tbDetalleTratamiento)
        tvPorcentajeAvance = findViewById(R.id.tvPorcentajeAvance)
        tvSesionesAvance = findViewById(R.id.tvSesionesAvance)
        tvFinEstimado = findViewById(R.id.tvFinEstimado)
        tvEstadoTratamiento = findViewById(R.id.tvEstadoTratamiento)
        rvAvances = findViewById(R.id.rvAvances)

        // 1. PRIMERO inicializamos el adaptador y lo asignamos al RecyclerView
        avanceAdaptador = AvanceAdaptador(emptyList()) { avanceSeleccionado ->
            presentador.SeleccionarAvance(avanceSeleccionado)
        }
        rvAvances.layoutManager = LinearLayoutManager(this)
        rvAvances.adapter = avanceAdaptador

        // 2. HASTA DESPUÉS inicializamos el presentador y cargamos los datos
        presentador = clsAvanceTratamientoPresentador(this)
        presentador.CargarDatosDetalle()
    }

    fun MostrarResumen(porcentaje: String, sesiones: String, finEstimado: String, estado: String) {
        tvPorcentajeAvance.text = porcentaje
        tvSesionesAvance.text = sesiones
        tvFinEstimado.text = finEstimado
        tvEstadoTratamiento.text = estado
    }

    fun MostrarListaAvances(listaAvances: List<DataAvance>) {
        avanceAdaptador.actualizarLista(listaAvances)
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}