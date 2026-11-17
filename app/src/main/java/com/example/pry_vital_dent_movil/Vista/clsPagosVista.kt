package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.example.pry_vital_dent_movil.R
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

        presentador = clsPagosPresentador(this)
        presentador.CargarDatosIniciales()

        btnPagarAhora.setOnClickListener {
            presentador.ClickPagarAhora()
        }
        configurarBarraInferior(3) // ¡Listo!

        // Listener para las pestañas (Realizados / Pendientes)
        tlPagos.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { presentador.CambiarPestana(it) }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        configurarBarraInferior(3)
    }

    fun MostrarSaldo(monto: String, concepto: String) {
        tvSaldoMonto.text = monto
        tvSaldoConcepto.text = concepto
    }

    fun MostrarListaPagos(lista: List<String>) {
        // Aquí conectarás tu Adapter para el RecyclerView de pagos
        Toast.makeText(this, "Se cargaron ${lista.size} registros de pago", Toast.LENGTH_SHORT).show()
    }

    fun IrARealizarPago() {
        val intent = Intent(this, clsRealizarPagoVista::class.java)
        startActivity(intent)
    }
}