package com.example.pry_vital_dent_movil.Modelo

// Reglas compartidas por los reproductores de audio y video.
// No usa nada de Android, por eso se puede probar con pruebas unitarias (src/test).
object clsReproductorModelo {

    const val SALTO_MS = 10_000L // botones de retroceder / adelantar 10 segundos

    // 75000 -> "1:15"   ·   3725000 -> "1:02:05"
    fun FormatearTiempo(milisegundos: Long): String {
        val totalSegundos = (if (milisegundos < 0) 0 else milisegundos) / 1000
        val horas = totalSegundos / 3600
        val minutos = (totalSegundos % 3600) / 60
        val segundos = totalSegundos % 60
        return if (horas > 0) {
            "%d:%02d:%02d".format(horas, minutos, segundos)
        } else {
            "%d:%02d".format(minutos, segundos)
        }
    }

    // Nueva posición al adelantar o retroceder, sin salirse del archivo
    fun CalcularSalto(posicionActual: Long, duracion: Long, salto: Long): Long {
        val nueva = posicionActual + salto
        return when {
            nueva < 0 -> 0
            duracion > 0 && nueva > duracion -> duracion
            else -> nueva
        }
    }

    // Solo se reproducen archivos del servidor (http/https)
    fun EsUrlValida(url: String?): Boolean {
        if (url.isNullOrBlank()) return false
        val minusculas = url.trim().lowercase()
        return minusculas.startsWith("https://") || minusculas.startsWith("http://")
    }
}
