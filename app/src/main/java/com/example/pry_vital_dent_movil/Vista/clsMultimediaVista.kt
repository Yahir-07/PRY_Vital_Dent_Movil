package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_vital_dent_movil.Adaptador.MultimediaAdaptador
import com.example.pry_vital_dent_movil.Modelo.DataMultimedia
import com.example.pry_vital_dent_movil.Presentador.clsMultimediaPresentador
import com.example.pry_vital_dent_movil.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.tabs.TabLayout

// Fase 4 · Lista de videos y audios educativos (con miniaturas cargadas por Glide)
class clsMultimediaVista : AppCompatActivity() {

    private lateinit var presentador: clsMultimediaPresentador
    private lateinit var adaptador: MultimediaAdaptador

    private lateinit var tbMultimedia: MaterialToolbar
    private lateinit var tlMultimedia: TabLayout
    private lateinit var rvMultimedia: RecyclerView
    private lateinit var pbCargaMultimedia: CircularProgressIndicator
    private lateinit var tvAvisoMultimedia: TextView
    private lateinit var btnReintentarMultimedia: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multimedia)

        tbMultimedia = findViewById(R.id.tbMultimedia)
        tlMultimedia = findViewById(R.id.tlMultimedia)
        rvMultimedia = findViewById(R.id.rvMultimedia)
        pbCargaMultimedia = findViewById(R.id.pbCargaMultimedia)
        tvAvisoMultimedia = findViewById(R.id.tvAvisoMultimedia)
        btnReintentarMultimedia = findViewById(R.id.btnReintentarMultimedia)

        tbMultimedia.setNavigationOnClickListener { finish() }

        // 1. Adaptador primero, después el presentador
        adaptador = MultimediaAdaptador(emptyList()) { elemento ->
            presentador.ClickElemento(elemento)
        }
        rvMultimedia.layoutManager = LinearLayoutManager(this)
        rvMultimedia.adapter = adaptador

        presentador = clsMultimediaPresentador(this)
        presentador.CargarContenido(0)

        // Pestañas: Todo / Videos / Audios
        tlMultimedia.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                presentador.CargarContenido(tab?.position ?: 0)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        btnReintentarMultimedia.setOnClickListener {
            presentador.CargarContenido(tlMultimedia.selectedTabPosition)
        }
    }

    // ---------- Estados de la pantalla: Carga, Éxito, Vacío, Error ----------

    fun MostrarCargando() {
        pbCargaMultimedia.visibility = View.VISIBLE
        rvMultimedia.visibility = View.GONE
        tvAvisoMultimedia.visibility = View.GONE
        btnReintentarMultimedia.visibility = View.GONE
    }

    fun MostrarLista(lista: List<DataMultimedia>) {
        adaptador.actualizarLista(lista)
        pbCargaMultimedia.visibility = View.GONE
        rvMultimedia.visibility = View.VISIBLE
        btnReintentarMultimedia.visibility = View.GONE
        MostrarAviso(getString(R.string.multimedia_aviso), esError = false)
    }

    fun MostrarVacio(mensaje: String) {
        adaptador.actualizarLista(emptyList())
        pbCargaMultimedia.visibility = View.GONE
        rvMultimedia.visibility = View.GONE
        btnReintentarMultimedia.visibility = View.GONE
        MostrarAviso(mensaje, esError = false)
    }

    fun MostrarError(mensaje: String) {
        adaptador.actualizarLista(emptyList())
        pbCargaMultimedia.visibility = View.GONE
        rvMultimedia.visibility = View.GONE
        btnReintentarMultimedia.visibility = View.VISIBLE
        MostrarAviso(mensaje, esError = true)
    }

    private fun MostrarAviso(mensaje: String, esError: Boolean) {
        tvAvisoMultimedia.text = mensaje
        tvAvisoMultimedia.setBackgroundResource(if (esError) R.drawable.bg_aviso_error else R.drawable.bg_aviso)
        tvAvisoMultimedia.setCompoundDrawablesRelativeWithIntrinsicBounds(
            if (esError) R.drawable.ic_punto_error else R.drawable.ic_punto_info, 0, 0, 0
        )
        tvAvisoMultimedia.visibility = View.VISIBLE
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    // ---------- Navegación ----------

    fun IrAReproductorVideo(elemento: DataMultimedia) {
        startActivity(CrearIntent(clsReproductorVideoVista::class.java, elemento))
    }

    fun IrAReproductorAudio(elemento: DataMultimedia) {
        startActivity(CrearIntent(clsReproductorAudioVista::class.java, elemento))
    }

    private fun CrearIntent(destino: Class<*>, elemento: DataMultimedia): Intent {
        val intent = Intent(this, destino)
        intent.putExtra(EXTRA_TITULO, elemento.titulo)
        intent.putExtra(EXTRA_DESCRIPCION, elemento.descripcion)
        intent.putExtra(EXTRA_URL, elemento.url)
        intent.putExtra(EXTRA_MINIATURA, elemento.urlMiniatura)
        return intent
    }

    companion object {
        const val EXTRA_TITULO = "multimedia_titulo"
        const val EXTRA_DESCRIPCION = "multimedia_descripcion"
        const val EXTRA_URL = "multimedia_url"
        const val EXTRA_MINIATURA = "multimedia_miniatura"
    }
}
