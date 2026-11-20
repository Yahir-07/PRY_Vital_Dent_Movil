package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsReproductorModelo
import com.example.pry_vital_dent_movil.Vista.clsReproductorAudioVista

// 4.2 · Lógica del reproductor de audio (MediaPlayer vive en la Vista)
class clsReproductorAudioPresentador(private val vista: clsReproductorAudioVista) {

    private var duracion: Long = 0L

    // Se conservan cuando la app pasa a segundo plano y regresa
    var posicionGuardada: Long = 0L
        private set
    private var reproducirAlPreparar: Boolean = true

    fun Iniciar(titulo: String, descripcion: String, url: String?, urlMiniatura: String?) {
        vista.MostrarDatos(titulo, descripcion, urlMiniatura ?: "")
        vista.HabilitarControles(false)

        if (!clsReproductorModelo.EsUrlValida(url)) {
            vista.MostrarError("Este audio no está disponible por el momento.")
            return
        }
        vista.PrepararAudio(url!!.trim())
    }

    // El MediaPlayer terminó de cargar el stream
    fun AudioListo(duracionMs: Long) {
        duracion = duracionMs
        vista.MostrarCarga(false)
        vista.HabilitarControles(true)
        vista.MostrarDuracion(duracion.toInt(), clsReproductorModelo.FormatearTiempo(duracion))

        if (posicionGuardada > 0) vista.IrAPosicion(posicionGuardada)
        ActualizarProgreso(posicionGuardada)

        if (reproducirAlPreparar) {
            vista.Reproducir()
            vista.MostrarEstadoReproduccion(true)
        } else {
            vista.MostrarEstadoReproduccion(false)
        }
    }

    fun ClickPlayPausa(estaReproduciendo: Boolean) {
        if (estaReproduciendo) {
            vista.Pausar()
            vista.MostrarEstadoReproduccion(false)
        } else {
            vista.Reproducir()
            vista.MostrarEstadoReproduccion(true)
        }
    }

    fun ClickRetroceder(posicionActual: Long) {
        Saltar(posicionActual, -clsReproductorModelo.SALTO_MS)
    }

    fun ClickAdelantar(posicionActual: Long) {
        Saltar(posicionActual, clsReproductorModelo.SALTO_MS)
    }

    private fun Saltar(posicionActual: Long, salto: Long) {
        val nueva = clsReproductorModelo.CalcularSalto(posicionActual, duracion, salto)
        vista.IrAPosicion(nueva)
        ActualizarProgreso(nueva)
    }

    // El usuario soltó la barra de progreso
    fun ClickBuscar(posicion: Long) {
        vista.IrAPosicion(posicion)
        ActualizarProgreso(posicion)
    }

    // Se llama cada medio segundo mientras suena, y al arrastrar la barra
    fun ActualizarProgreso(posicion: Long) {
        vista.MostrarProgreso(posicion.toInt(), clsReproductorModelo.FormatearTiempo(posicion))
    }

    fun AudioTerminado() {
        posicionGuardada = 0L
        vista.MostrarEstadoReproduccion(false)
        vista.IrAPosicion(0)
        ActualizarProgreso(0)
    }

    fun GuardarEstado(posicion: Long, reproduciendo: Boolean) {
        posicionGuardada = if (posicion < 0) 0 else posicion
        reproducirAlPreparar = reproduciendo
    }

    fun ErrorReproduccion() {
        vista.MostrarCarga(false)
        vista.HabilitarControles(false)
        vista.MostrarEstadoReproduccion(false)
        vista.MostrarError("No se pudo reproducir el audio. Revisa tu conexión a internet e inténtalo de nuevo.")
    }
}
