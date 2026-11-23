package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.DataMultimedia
import com.example.pry_vital_dent_movil.Modelo.clsMultimediaModelo
import com.example.pry_vital_dent_movil.Vista.clsMultimediaVista

class clsMultimediaPresentador(private val vista: clsMultimediaVista) {

    private val modelo = clsMultimediaModelo(vista)

    // 0 = Todo, 1 = Videos, 2 = Audios (pestañas de la pantalla)
    fun CargarContenido(posicionPestana: Int) {
        vista.MostrarCargando()

        modelo.ObtenerMultimedia(TipoDePestana(posicionPestana)) { exito, mensaje, lista ->
            when {
                !exito -> vista.MostrarError(mensaje)
                lista.isEmpty() -> vista.MostrarVacio("Todavía no hay contenido en esta sección.")
                else -> vista.MostrarLista(lista)
            }
        }
    }

    fun ClickElemento(elemento: DataMultimedia) {
        when (elemento.tipo) {
            DataMultimedia.TIPO_VIDEO -> vista.IrAReproductorVideo(elemento)
            DataMultimedia.TIPO_AUDIO -> vista.IrAReproductorAudio(elemento)
            else -> vista.MostrarMensaje("Formato no compatible")
        }
    }

    companion object {
        fun TipoDePestana(posicion: Int): String? = when (posicion) {
            1 -> DataMultimedia.TIPO_VIDEO
            2 -> DataMultimedia.TIPO_AUDIO
            else -> null // null = el servidor manda todo
        }
    }
}
