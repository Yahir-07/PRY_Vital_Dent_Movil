package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.clsReproductorModelo
import com.example.pry_vital_dent_movil.Vista.clsReproductorVideoVista

// 4.3 · Lógica del reproductor de video (ExoPlayer vive en la Vista)
class clsReproductorVideoPresentador(private val vista: clsReproductorVideoVista) {

    // Se conservan cuando la app pasa a segundo plano y regresa
    var posicionGuardada: Long = 0L
        private set
    var reproducirAlVolver: Boolean = true
        private set
    var pantallaCompleta: Boolean = false
        private set

    fun Iniciar(titulo: String, descripcion: String, url: String?) {
        vista.MostrarDatos(titulo, descripcion)

        if (!clsReproductorModelo.EsUrlValida(url)) {
            vista.MostrarError("Este video no está disponible por el momento.")
            return
        }
        vista.PrepararVideo(url!!.trim())
    }

    fun GuardarEstado(posicion: Long, reproduciendo: Boolean) {
        posicionGuardada = if (posicion < 0) 0 else posicion
        reproducirAlVolver = reproduciendo
    }

    fun CambioCarga(cargando: Boolean) {
        vista.MostrarCarga(cargando)
    }

    fun VideoTerminado() {
        // Al terminar se regresa al inicio en pausa para poder verlo otra vez
        posicionGuardada = 0L
        reproducirAlVolver = false
        if (pantallaCompleta) ClickPantallaCompleta(false)
    }

    fun ErrorReproduccion() {
        vista.MostrarCarga(false)
        if (pantallaCompleta) ClickPantallaCompleta(false) // para que se vea el aviso
        vista.MostrarError("No se pudo reproducir el video. Revisa tu conexión a internet e inténtalo de nuevo.")
    }

    fun ClickPantallaCompleta(activar: Boolean) {
        pantallaCompleta = activar
        vista.AplicarPantallaCompleta(activar)
    }

    // Botón atrás: primero sale de pantalla completa, luego cierra la pantalla
    fun ClickRegresar() {
        if (pantallaCompleta) {
            ClickPantallaCompleta(false)
        } else {
            vista.Cerrar()
        }
    }
}
