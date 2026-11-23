package com.example.pry_vital_dent_movil.Vista

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.widget.NestedScrollView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.pry_vital_dent_movil.Presentador.clsReproductorVideoPresentador
import com.example.pry_vital_dent_movil.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.progressindicator.CircularProgressIndicator

// 4.3 · Reproductor de video hospedado en el servidor (ExoPlayer de Media3)
// con controles multimedia (play/pausa, barra de progreso, adelantar/retroceder) y pantalla completa.
@OptIn(UnstableApi::class)
class clsReproductorVideoVista : AppCompatActivity() {

    private lateinit var presentador: clsReproductorVideoPresentador

    private lateinit var clRaizVideo: ConstraintLayout
    private lateinit var tbReproductorVideo: MaterialToolbar
    private lateinit var flContenedorVideo: FrameLayout
    private lateinit var pvVideo: PlayerView
    private lateinit var pbCargaVideo: CircularProgressIndicator
    private lateinit var svDetallesVideo: NestedScrollView
    private lateinit var tvTituloVideo: TextView
    private lateinit var tvDescripcionVideo: TextView
    private lateinit var tvAvisoVideo: TextView

    private var reproductor: ExoPlayer? = null
    private var urlVideo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reproductor_video)

        clRaizVideo = findViewById(R.id.clRaizVideo)
        tbReproductorVideo = findViewById(R.id.tbReproductorVideo)
        flContenedorVideo = findViewById(R.id.flContenedorVideo)
        pvVideo = findViewById(R.id.pvVideo)
        pbCargaVideo = findViewById(R.id.pbCargaVideo)
        svDetallesVideo = findViewById(R.id.svDetallesVideo)
        tvTituloVideo = findViewById(R.id.tvTituloVideo)
        tvDescripcionVideo = findViewById(R.id.tvDescripcionVideo)
        tvAvisoVideo = findViewById(R.id.tvAvisoVideo)

        presentador = clsReproductorVideoPresentador(this)

        tbReproductorVideo.setNavigationOnClickListener { presentador.ClickRegresar() }

        // Botón de pantalla completa dentro de los controles del reproductor
        pvVideo.setFullscreenButtonClickListener { activar ->
            presentador.ClickPantallaCompleta(activar)
        }

        // Botón/gesto "atrás" del teléfono
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                presentador.ClickRegresar()
            }
        })

        presentador.Iniciar(
            intent.getStringExtra(clsMultimediaVista.EXTRA_TITULO) ?: "",
            intent.getStringExtra(clsMultimediaVista.EXTRA_DESCRIPCION) ?: "",
            intent.getStringExtra(clsMultimediaVista.EXTRA_URL)
        )
    }

    // El reproductor se crea al mostrarse la pantalla y se libera al ocultarse
    override fun onStart() {
        super.onStart()
        if (urlVideo.isNotEmpty()) CrearReproductor()
    }

    override fun onStop() {
        super.onStop()
        LiberarReproductor()
    }

    private fun CrearReproductor() {
        if (reproductor != null) return

        val nuevo = ExoPlayer.Builder(this).build()
        pvVideo.player = nuevo

        nuevo.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(estado: Int) {
                presentador.CambioCarga(estado == Player.STATE_BUFFERING)
                if (estado == Player.STATE_ENDED) {
                    presentador.VideoTerminado()
                    reproductor?.seekTo(0)
                    reproductor?.pause()
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                presentador.ErrorReproduccion()
            }
        })

        nuevo.setMediaItem(MediaItem.fromUri(urlVideo))
        nuevo.seekTo(presentador.posicionGuardada)
        nuevo.playWhenReady = presentador.reproducirAlVolver
        nuevo.prepare()
        reproductor = nuevo
    }

    private fun LiberarReproductor() {
        reproductor?.let {
            presentador.GuardarEstado(it.currentPosition, it.playWhenReady)
            it.release()
        }
        reproductor = null
        pvVideo.player = null
    }

    // ---------- Métodos que usa el Presentador ----------

    fun MostrarDatos(titulo: String, descripcion: String) {
        tvTituloVideo.text = titulo
        tvDescripcionVideo.text = descripcion
    }

    fun PrepararVideo(url: String) {
        urlVideo = url
        tvAvisoVideo.visibility = View.GONE
    }

    fun MostrarCarga(cargando: Boolean) {
        pbCargaVideo.visibility = if (cargando) View.VISIBLE else View.GONE
    }

    fun MostrarError(mensaje: String) {
        tvAvisoVideo.text = mensaje
        tvAvisoVideo.visibility = View.VISIBLE
    }

    fun AplicarPantallaCompleta(activar: Boolean) {
        val controlador = WindowCompat.getInsetsController(window, window.decorView)
        val parametros = flContenedorVideo.layoutParams as ConstraintLayout.LayoutParams

        if (activar) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            controlador.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controlador.hide(WindowInsetsCompat.Type.systemBars())

            tbReproductorVideo.visibility = View.GONE
            svDetallesVideo.visibility = View.GONE
            clRaizVideo.setBackgroundColor(ContextCompat.getColor(this, R.color.black))

            parametros.dimensionRatio = null
            parametros.topToBottom = ConstraintLayout.LayoutParams.UNSET
            parametros.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            parametros.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            controlador.show(WindowInsetsCompat.Type.systemBars())

            tbReproductorVideo.visibility = View.VISIBLE
            svDetallesVideo.visibility = View.VISIBLE
            clRaizVideo.setBackgroundColor(ContextCompat.getColor(this, R.color.vd_fondo))

            parametros.dimensionRatio = "16:9"
            parametros.topToTop = ConstraintLayout.LayoutParams.UNSET
            parametros.bottomToBottom = ConstraintLayout.LayoutParams.UNSET
            parametros.topToBottom = R.id.tbReproductorVideo
        }

        flContenedorVideo.layoutParams = parametros
        pvVideo.setFullscreenButtonState(activar)
    }

    fun Cerrar() {
        finish()
    }
}
