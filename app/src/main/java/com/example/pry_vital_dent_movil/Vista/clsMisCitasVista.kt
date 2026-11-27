package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_vital_dent_movil.Adaptador.MisCitasAdaptador
import com.example.pry_vital_dent_movil.Modelo.DataCita
import com.google.android.material.tabs.TabLayout
import com.google.android.material.button.MaterialButton
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsMisCitasPresentador
import com.google.android.material.appbar.MaterialToolbar

class clsMisCitasVista : AppCompatActivity() {

    private lateinit var presentador: clsMisCitasPresentador
    private lateinit var tbCitas: MaterialToolbar
    private lateinit var tvSinConexion: TextView
    private lateinit var tlCitas: TabLayout
    private lateinit var rvCitas: RecyclerView
    private lateinit var btnAgendarNuevaCita: MaterialButton
    private lateinit var tvAviso: TextView
    private lateinit var MisCitasAdaptador: MisCitasAdaptador


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_citas)

        tbCitas = findViewById(R.id.tbCitas)
        tvSinConexion = findViewById(R.id.tvSinConexion)
        tlCitas = findViewById(R.id.tlCitas)
        rvCitas = findViewById(R.id.rvCitas)
        btnAgendarNuevaCita = findViewById(R.id.btnAgendarNuevaCita)
        tvAviso = findViewById(R.id.tvAviso)

        // 1. PRIMERO inicializamos el adaptador y lo configuramos en el RecyclerView
        MisCitasAdaptador = MisCitasAdaptador(emptyList()) { citaSeleccionada ->
            // Acción al hacer clic en una cita (opcional)
        }
        rvCitas.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rvCitas.adapter = MisCitasAdaptador

        // 2. HASTA DESPUÉS inicializamos el presentador y cargamos los datos
        presentador = clsMisCitasPresentador(this)
        presentador.CargarCitasProximas()

        tlCitas.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> presentador.CargarCitasProximas()
                    1 -> presentador.CargarCitasAnteriores()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Clic en el botón para agendar nueva cita
        btnAgendarNuevaCita.setOnClickListener {
            presentador.ClickAgendarNuevaCita()
        }
        configurarBarraInferior(1)
    }

    fun MostrarListaCitas(lista: List<DataCita>) {
        MisCitasAdaptador.actualizarLista(lista)
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

    fun MostrarAvisoSinConexion(mostrar: Boolean) {
        tvSinConexion.visibility = if (mostrar) View.VISIBLE else View.GONE
    }

    fun IrAAgendarCita() {
        val intent = Intent(this, clsAgendarCitaVista::class.java)
        startActivity(intent)
    }
}