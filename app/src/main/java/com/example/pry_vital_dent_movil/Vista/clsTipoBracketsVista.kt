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
import android.widget.ImageView

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
    private lateinit var ivBracketsMetalicos: ImageView
    private lateinit var ivBracketsCeramicos: ImageView
    private lateinit var ivBracketsZafiro: ImageView
    private lateinit var ivBracketsAutoligables: ImageView

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

        ivBracketsMetalicos = findViewById(R.id.ivBracketsMetalicos)
        ivBracketsCeramicos = findViewById(R.id.ivBracketsCeramicos)
        ivBracketsZafiro = findViewById(R.id.ivBracketsZafiro)
        ivBracketsAutoligables = findViewById(R.id.ivBracketsAutoligables)

        presentador = clsTipoBracketsPresentador(this)
        presentador.CargarImagenes()

        tvVerCaracteristicasMetalicos.setOnClickListener {
            val oculto = tvCaracteristicasMetalicos.visibility == View.GONE
            val mostrar = presentador.ClickVerCaracteristicas(!oculto)
            tvCaracteristicasMetalicos.visibility = if (mostrar) View.VISIBLE else View.GONE
            tvVerCaracteristicasMetalicos.text = if (mostrar) "Ocultar características" else "Ver características"
            IgualarAlturas()
        }

        tvVerCaracteristicasCeramicos.setOnClickListener {
            val oculto = tvCaracteristicasCeramicos.visibility == View.GONE
            val mostrar = presentador.ClickVerCaracteristicas(!oculto)
            tvCaracteristicasCeramicos.visibility = if (mostrar) View.VISIBLE else View.GONE
            tvVerCaracteristicasCeramicos.text = if (mostrar) "Ocultar características" else "Ver características"
            IgualarAlturas()
        }

        tvVerCaracteristicasZafiro.setOnClickListener {
            val oculto = tvCaracteristicasZafiro.visibility == View.GONE
            val mostrar = presentador.ClickVerCaracteristicas(!oculto)
            tvCaracteristicasZafiro.visibility = if (mostrar) View.VISIBLE else View.GONE
            tvVerCaracteristicasZafiro.text = if (mostrar) "Ocultar características" else "Ver características"
            IgualarAlturas()
        }

        tvVerCaracteristicasAutoligables.setOnClickListener {
            val oculto = tvCaracteristicasAutoligables.visibility == View.GONE
            val mostrar = presentador.ClickVerCaracteristicas(!oculto)
            tvCaracteristicasAutoligables.visibility = if (mostrar) View.VISIBLE else View.GONE
            tvVerCaracteristicasAutoligables.text = if (mostrar) "Ocultar características" else "Ver características"
            IgualarAlturas()
        }

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

        IgualarAlturas()
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun MostrarFiltroTodos() {
        cvBracketsMetalicos.visibility = View.VISIBLE
        cvBracketsCeramicos.visibility = View.VISIBLE
        cvBracketsZafiro.visibility = View.VISIBLE
        cvBracketsAutoligables.visibility = View.VISIBLE
        IgualarAlturas()
    }

    fun MostrarFiltroEsteticos() {
        cvBracketsMetalicos.visibility = View.GONE
        cvBracketsCeramicos.visibility = View.VISIBLE
        cvBracketsZafiro.visibility = View.VISIBLE
        cvBracketsAutoligables.visibility = View.VISIBLE
        IgualarAlturas()
    }

    fun MostrarFiltroClasicos() {
        cvBracketsMetalicos.visibility = View.VISIBLE
        cvBracketsCeramicos.visibility = View.GONE
        cvBracketsZafiro.visibility = View.GONE
        cvBracketsAutoligables.visibility = View.GONE
        IgualarAlturas()
    }

    fun IrAAgendarValoracion(motivo: String) {
        val intent = Intent(this, clsAgendarCitaVista::class.java)
        intent.putExtra(clsAgendarCitaVista.EXTRA_MOTIVO, motivo) // le pasamos el motivo a Agendar cita
        startActivity(intent)
    }

    fun MostrarImagenes(urlMetalicos: String?, urlCeramicos: String?, urlZafiro: String?, urlAutoligables: String?) {
        ivBracketsMetalicos.cargarImagen(urlMetalicos, R.drawable.img_brackets_metalicos)
        ivBracketsCeramicos.cargarImagen(urlCeramicos, R.drawable.img_brackets_ceramicos)
        ivBracketsZafiro.cargarImagen(urlZafiro, R.drawable.img_brackets_zafiro)
        ivBracketsAutoligables.cargarImagen(urlAutoligables, R.drawable.img_brackets_autoligables)
    }

    // Iguala la altura de las tarjetas que quedan en la misma fila (el Flow las acomoda de 2 en 2).
    // Se le pone altura mínima al contenido de cada tarjeta; el <Space> del XML empuja el botón hasta abajo.
    private fun IgualarAlturas() {
        cvBracketsMetalicos.post {
            listOf(cvBracketsMetalicos, cvBracketsCeramicos, cvBracketsZafiro, cvBracketsAutoligables)
                .filter { it.visibility == View.VISIBLE }
                .chunked(2)
                .forEach { fila ->
                    val contenidos = fila.map { it.getChildAt(0) }
                    // Altura natural de cada contenido (sin el mínimo anterior)
                    val alto = contenidos.maxOf { contenido ->
                        contenido.minimumHeight = 0
                        contenido.measure(
                            View.MeasureSpec.makeMeasureSpec(contenido.width, View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                        )
                        contenido.measuredHeight
                    }
                    contenidos.forEach { it.minimumHeight = alto }
                }
        }
    }
}