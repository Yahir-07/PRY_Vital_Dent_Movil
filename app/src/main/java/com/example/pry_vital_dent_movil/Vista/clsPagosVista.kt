package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Adaptador.PagoAdaptador
import com.example.pry_vital_dent_movil.Modelo.DataPago
import com.example.pry_vital_dent_movil.Presentador.clsPagosPresentador
import com.google.android.material.appbar.MaterialToolbar

class clsPagosVista : AppCompatActivity() {

    private lateinit var presentador: clsPagosPresentador
    private lateinit var tbPagos: MaterialToolbar
    private lateinit var cvSaldoPendiente: MaterialCardView
    private lateinit var tvSaldoMonto: TextView
    private lateinit var tvSaldoConcepto: TextView
    private lateinit var btnPagarAhora: MaterialButton
    private lateinit var tlPagos: TabLayout
    private lateinit var rvPagos: RecyclerView
    private lateinit var pagoAdaptador: PagoAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_pagos)

        tbPagos = findViewById(R.id.tbPagos)
        cvSaldoPendiente = findViewById(R.id.cvSaldoPendiente)
        tvSaldoMonto = findViewById(R.id.tvSaldoMonto)
        tvSaldoConcepto = findViewById(R.id.tvSaldoConcepto)
        btnPagarAhora = findViewById(R.id.btnPagarAhora)
        tlPagos = findViewById(R.id.tlPagos)
        rvPagos = findViewById(R.id.rvPagos)

        // 1. PRIMERO inicializamos el adaptador y lo asignamos al RecyclerView
        pagoAdaptador = PagoAdaptador(emptyList()) { pagoSeleccionado ->
            presentador.SeleccionarPago(pagoSeleccionado)
        }
        rvPagos.layoutManager = LinearLayoutManager(this)
        rvPagos.adapter = pagoAdaptador

        // 2. HASTA DESPUÉS inicializamos el presentador y cargamos los datos
        presentador = clsPagosPresentador(this)
        presentador.CargarDatosIniciales()

        btnPagarAhora.setOnClickListener {
            presentador.ClickPagarAhora()
        }

        // Listener para las pestañas (Realizados / Pendientes)
        tlPagos.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { presentador.CambiarPestana(it) }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Nota: Asegúrate de tener implementada tu función configurarBarraInferior si la usas en tu app
        // configurarBarraInferior(3)
    }

    fun MostrarSaldo(monto: String, concepto: String) {
        tvSaldoMonto.text = monto
        tvSaldoConcepto.text = concepto
    }

    fun MostrarListaPagos(lista: List<DataPago>) {
        pagoAdaptador.actualizarLista(lista)
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun IrARealizarPago() {
        val intent = Intent(this, clsRealizarPagoVista::class.java)
        startActivity(intent)
    }
}