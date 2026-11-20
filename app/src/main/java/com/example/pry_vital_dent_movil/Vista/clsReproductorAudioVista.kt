package com.example.pry_vital_dent_movil.Vista

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.pry_vital_dent_movil.Presentador.clsReproductorAudioPresentador
import com.example.pry_vital_dent_movil.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator

// 4.2 · Reproductor de audio en streaming desde el hosting (MediaPlayer)
// con play/pausa, barra de progreso, tiempo actual/total y saltos de 10 segundos.
class clsReproductorAudioVista : AppCompatActivity() {

    private lateinit var presentador: clsReproductorAudioPresentador

    private lateinit var tbReproductorAudio: MaterialToolbar
    private lateinit var ivPortadaAudio: ImageView
    private lateinit var tvTituloAudio: TextView
    private lateinit var tvDescripcionAudio: TextView
    private lateinit var sbProgresoAudio: SeekBar
    private lateinit var tvTiempoActualAudio: TextView
    private lateinit var tvTiempoTotalAudio: TextView
    private lateinit var btnRetrocederAudio: MaterialButton
    private lateinit var fabPlayPausaAudio: FloatingActionButton
    private lateinit var btnAdelantarAudio: MaterialButton
    private lateinit var pbCargaAudio: CircularProgressIndicator
    private lateinit var tvAvisoAudio: TextView

    private var reproductor: MediaPlayer? = null
    private var urlAudio: String = ""
    private var preparado = false
    private var usuarioArrastrando = false

    // Actualiza la barra de progreso cada medio segundo mientras suena
    private val manejador = Handler(Looper.getMainLooper())
    private val actualizarBarra = object : Runnable {
        override fun run() {
            val mp = reproductor
            if (mp != null && preparado && !usuarioArrastrando) {
                presentador.ActualizarProgreso(mp.currentPosition.toLong())
            }
            manejador.postDelayed(this, 500)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reproductor_audio)

        tbReproductorAudio = findViewById(R.id.tbReproductorAudio)
        ivPortadaAudio = findViewById(R.id.ivPortadaAudio)
        tvTituloAudio = findViewById(R.id.tvTituloAudio)
        tvDescripcionAudio = findViewById(R.id.tvDescripcionAudio)
        sbProgresoAudio = findViewById(R.id.sbProgresoAudio)
        tvTiempoActualAudio = findViewById(R.id.tvTiempoActualAudio)
        tvTiempoTotalAudio = findViewById(R.id.tvTiempoTotalAudio)
        btnRetrocederAudio = findViewById(R.id.btnRetrocederAudio)
        fabPlayPausaAudio = findViewById(R.id.fabPlayPausaAudio)
        btnAdelantarAudio = findViewById(R.id.btnAdelantarAudio)
        pbCargaAudio = findViewById(R.id.pbCargaAudio)
        tvAvisoAudio = findViewById(R.id.tvAvisoAudio)

        presentador = clsReproductorAudioPresentador(this)

        tbReproductorAudio.setNavigationOnClickListener { finish() }

        fabPlayPausaAudio.setOnClickListener {
            presentador.ClickPlayPausa(reproductor?.isPlaying == true)
        }
        btnRetrocederAudio.setOnClickListener {
            presentador.ClickRetroceder(reproductor?.currentPosition?.toLong() ?: 0L)
        }
        btnAdelantarAudio.setOnClickListener {
            presentador.ClickAdelantar(reproductor?.currentPosition?.toLong() ?: 0L)
        }

        sbProgresoAudio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(barra: SeekBar?, progreso: Int, delUsuario: Boolean) {
                if (delUsuario) presentador.ActualizarProgreso(progreso.toLong())
            }

            override fun onStartTrackingTouch(barra: SeekBar?) {
                usuarioArrastrando = true
            }

            override fun onStopTrackingTouch(barra: SeekBar?) {
                usuarioArrastrando = false
                presentador.ClickBuscar((barra?.progress ?: 0).toLong())
            }
        })

        presentador.Iniciar(
            intent.getStringExtra(clsMultimediaVista.EXTRA_TITULO) ?: "",
            intent.getStringExtra(clsMultimediaVista.EXTRA_DESCRIPCION) ?: "",
            intent.getStringExtra(clsMultimediaVista.EXTRA_URL),
            intent.getStringExtra(clsMultimediaVista.EXTRA_MINIATURA)
        )
    }

    // El reproductor se crea al mostrarse la pantalla y se libera al ocultarse
    override fun onStart() {
        super.onStart()
        if (urlAudio.isNotEmpty()) CrearReproductor()
        manejador.post(actualizarBarra)
    }

    override fun onStop() {
        super.onStop()
        manejador.removeCallbacks(actualizarBarra)
        LiberarReproductor()
    }

    private fun CrearReproductor() {
        if (reproductor != null) return
        preparado = false
        MostrarCarga(true)
        tvAvisoAudio.visibility = View.GONE

        val nuevo = MediaPlayer()
        nuevo.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
        )
        nuevo.setOnPreparedListener { mp ->
            preparado = true
            presentador.AudioListo(mp.duration.toLong())
        }
        nuevo.setOnCompletionListener { presentador.AudioTerminado() }
        nuevo.setOnBufferingUpdateListener { mp, porcentaje ->
            if (preparado) sbProgresoAudio.secondaryProgress = mp.duration * porcentaje / 100
        }
        nuevo.setOnErrorListener { _, _, _ ->
            preparado = false
            presentador.ErrorReproduccion()
            true
        }

        try {
            nuevo.setDataSource(urlAudio)
            nuevo.prepareAsync() // carga el stream en segundo plano
            reproductor = nuevo
        } catch (e: Exception) {
            nuevo.release()
            presentador.ErrorReproduccion()
        }
    }

    private fun LiberarReproductor() {
        reproductor?.let {
            if (preparado) presentador.GuardarEstado(it.currentPosition.toLong(), it.isPlaying)
            it.release()
        }
        reproductor = null
        preparado = false
    }

    // ---------- Métodos que usa el Presentador ----------

    fun MostrarDatos(titulo: String, descripcion: String, urlMiniatura: String) {
        tvTituloAudio.text = titulo
        tvDescripcionAudio.text = descripcion

        // 4.1 · Portada remota con Glide
        Glide.with(this)
            .load(urlMiniatura.ifBlank { null })
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.bg_placeholder)
            .error(R.drawable.img_multimedia_error)
            .fallback(R.drawable.img_multimedia_error)
            .transform(CenterCrop(), RoundedCorners(resources.getDimensionPixelSize(R.dimen.vd_radio_xl)))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ivPortadaAudio)
    }

    fun PrepararAudio(url: String) {
        urlAudio = url
    }

    fun MostrarCarga(cargando: Boolean) {
        pbCargaAudio.visibility = if (cargando) View.VISIBLE else View.INVISIBLE
    }

    fun HabilitarControles(habilitar: Boolean) {
        fabPlayPausaAudio.isEnabled = habilitar
        btnRetrocederAudio.isEnabled = habilitar
        btnAdelantarAudio.isEnabled = habilitar
        sbProgresoAudio.isEnabled = habilitar
    }

    fun MostrarDuracion(duracionMs: Int, texto: String) {
        sbProgresoAudio.max = duracionMs
        tvTiempoTotalAudio.text = texto
    }

    fun MostrarProgreso(posicionMs: Int, texto: String) {
        if (!usuarioArrastrando) sbProgresoAudio.progress = posicionMs
        tvTiempoActualAudio.text = texto
    }

    fun MostrarEstadoReproduccion(reproduciendo: Boolean) {
        fabPlayPausaAudio.setImageResource(if (reproduciendo) R.drawable.ic_pausa else R.drawable.ic_play)
        fabPlayPausaAudio.contentDescription =
            getString(if (reproduciendo) R.string.cd_pausar else R.string.cd_reproducir)
    }

    fun Reproducir() {
        if (preparado) reproductor?.start()
    }

    fun Pausar() {
        if (preparado) reproductor?.pause()
    }

    fun IrAPosicion(posicionMs: Long) {
        if (preparado) reproductor?.seekTo(posicionMs.toInt())
    }

    fun MostrarError(mensaje: String) {
        tvAvisoAudio.text = mensaje
        tvAvisoAudio.visibility = View.VISIBLE
    }
}
