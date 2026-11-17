package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsTipoBracketsPresentador
import com.google.android.material.appbar.MaterialToolbar

class clsTipoBracketsVista : AppCompatActivity() {
    private lateinit var presentador: clsTipoBracketsPresentador
    private lateinit var tbTiposBrackets: MaterialToolbar
    private lateinit var tlFiltroBrackets: TabLayout
    private lateinit var cvBracketsMetalicos: MaterialCardView
    private lateinit var tvVerCaracteristicasMetalicos: TextView
    private lateinit var tvCaracteristicasMetalicos: TextView
    private lateinit var btnSolicitarValoracionMetalicos: MaterialButton
    private lateinit var cvBracketsCeramicos: MaterialCardView
    private lateinit var tvVerCaracteristicasCeramicos: TextView
    private lateinit var tvCaracteristicasCeramicos: TextView
    private lateinit var btnSolicitarValoracionCeramicos: MaterialButton
    private lateinit var cvBracketsZafiro: MaterialCardView
    private lateinit var tvVerCaracteristicasZafiro: TextView
    private lateinit var tvCaracteristicasZafiro: TextView
    private lateinit var btnSolicitarValoracionZafiro: MaterialButton
    private lateinit var cvBracketsAutoligables: MaterialCardView
    private lateinit var tvVerCaracteristicasAutoligables: TextView
    private lateinit var tvCaracteristicasAutoligables: TextView
    private lateinit var btnSolicitarValoracionAutoligables: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_tipos_brackets)

        tbTiposBrackets = findViewById(R.id.tbTiposBrackets)
        tlFiltroBrackets = findViewById(R.id.tlFiltroBrackets)

        cvBracketsMetalicos = findViewById(R.id.cvBracketsMetalicos)
        tvVerCaracteristicasMetalicos = findViewById(R.id.tvVerCaracteristicasMetalicos)
        tvCaracteristicasMetalicos = findViewById(R.id.tvCaracteristicasMetalicos)
        btnSolicitarValoracionMetalicos = findViewById(R.id.btnSolicitarValoracionMetalicos)

        cvBracketsCeramicos = findViewById(R.id.cvBracketsCeramicos)
        tvVerCaracteristicasCeramicos = findViewById(R.id.tvVerCaracteristicasCeramicos)
        tvCaracteristicasCeramicos = findViewById(R.id.tvCaracteristicasCeramicos)
        btnSolicitarValoracionCeramicos = findViewById(R.id.btnSolicitarValoracionCeramicos)

        cvBracketsZafiro = findViewById(R.id.cvBracketsZafiro)
        tvVerCaracteristicasZafiro = findViewById(R.id.tvVerCaracteristicasZafiro)
        tvCaracteristicasZafiro = findViewById(R.id.tvCaracteristicasZafiro)
        btnSolicitarValoracionZafiro = findViewById(R.id.btnSolicitarValoracionZafiro)

        cvBracketsAutoligables = findViewById(R.id.cvBracketsAutoligables)
        tvVerCaracteristicasAutoligables = findViewById(R.id.tvVerCaracteristicasAutoligables)
        tvCaracteristicasAutoligables = findViewById(R.id.tvCaracteristicasAutoligables)
        btnSolicitarValoracionAutoligables = findViewById(R.id.btnSolicitarValoracionAutoligables)

        presentador = clsTipoBracketsPresentador(this)

        // Configurar despliegue de características (Metálicos)
        tvVerCaracteristicasMetalicos.setOnClickListener {
            val oculto = tvCaracteristicasMetalicos.visibility == View.GONE
            val mostrar = presentador.ClickVerCaracteristicas(!oculto)
            tvCaracteristicasMetalicos.visibility = if (mostrar) View.VISIBLE else View.GONE
            tvVerCaracteristicasMetalicos.text = if (mostrar) "Ocultar características" else "Ver características"
        }

        // Configurar despliegue de características (Cerámicos)
        tvVerCaracteristicasCeramicos.setOnClickListener {
            val oculto = tvCaracteristicasCeramicos.visibility == View.GONE
            val mostrar = presentador.ClickVerCaracteristicas(!oculto)
            tvCaracteristicasCeramicos.visibility = if (mostrar) View.VISIBLE else View.GONE
            tvVerCaracteristicasCeramicos.text = if (mostrar) "Ocultar características" else "Ver características"
        }

        // Configurar despliegue de características (Zafiro)
        tvVerCaracteristicasZafiro.setOnClickListener {
            val oculto = tvCaracteristicasZafiro.visibility == View.GONE
            val mostrar = presentador.ClickVerCaracteristicas(!oculto)
            tvCaracteristicasZafiro.visibility = if (mostrar) View.VISIBLE else View.GONE
            tvVerCaracteristicasZafiro.text = if (mostrar) "Ocultar características" else "Ver características"
        }

        // Configurar despliegue de características (Autoligables)
        tvVerCaracteristicasAutoligables.setOnClickListener {
            val oculto = tvCaracteristicasAutoligables.visibility == View.GONE
            val mostrar = presentador.ClickVerCaracteristicas(!oculto)
            tvCaracteristicasAutoligables.visibility = if (mostrar) View.VISIBLE else View.GONE
            tvVerCaracteristicasAutoligables.text = if (mostrar) "Ocultar características" else "Ver características"
        }

        // Botones de solicitar valoración
        btnSolicitarValoracionMetalicos.setOnClickListener { presentador.ClickSolicitarValoracion("Brackets Metálicos") }
        btnSolicitarValoracionCeramicos.setOnClickListener { presentador.ClickSolicitarValoracion("Brackets Cerámicos") }
        btnSolicitarValoracionZafiro.setOnClickListener { presentador.ClickSolicitarValoracion("Brackets de Zafiro") }
        btnSolicitarValoracionAutoligables.setOnClickListener { presentador.ClickSolicitarValoracion("Brackets Autoligables") }

        // Filtros por pestañas (Todos, Estéticos, Clásicos)
        tlFiltroBrackets.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { presentador.FiltrarBrackets(it) }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun MostrarFiltroTodos() {
        cvBracketsMetalicos.visibility = View.VISIBLE
        cvBracketsCeramicos.visibility = View.VISIBLE
        cvBracketsZafiro.visibility = View.VISIBLE
        cvBracketsAutoligables.visibility = View.VISIBLE
    }

    fun MostrarFiltroEsteticos() {
        cvBracketsMetalicos.visibility = View.GONE
        cvBracketsCeramicos.visibility = View.VISIBLE
        cvBracketsZafiro.visibility = View.VISIBLE
        cvBracketsAutoligables.visibility = View.VISIBLE
    }

    fun MostrarFiltroClasicos() {
        cvBracketsMetalicos.visibility = View.VISIBLE
        cvBracketsCeramicos.visibility = View.GONE
        cvBracketsZafiro.visibility = View.GONE
        cvBracketsAutoligables.visibility = View.GONE
    }

    fun IrASolicitudExitosa() {
        val intent = Intent(this, clsAgendarCitaVista::class.java)
        startActivity(intent)
    }
}