package com.example.pry_vital_dent_movil.Modelo

// Un video o audio educativo que entrega multimedia.php
// Los archivos están hospedados en el servidor: APIS_proyecto/multimedia/
data class DataMultimedia(
    val id: String = "",
    val tipo: String = "",          // "video" o "audio"
    val titulo: String = "",
    val descripcion: String = "",
    val duracion: String = "",      // texto para mostrar, por ejemplo "0:46"
    val url: String = "",           // archivo .mp4 o .mp3 en el hosting
    val urlMiniatura: String = ""   // imagen .jpg que se carga con Glide
) {
    val esVideo: Boolean
        get() = tipo == TIPO_VIDEO

    companion object {
        const val TIPO_VIDEO = "video"
        const val TIPO_AUDIO = "audio"
    }
}
