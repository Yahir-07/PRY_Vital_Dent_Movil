package com.example.pry_vital_dent_movil.Modelo

data class DataTratamiento(
    val idTratamiento: String,
    val nombre: String,
    val detalle: String,
    val estado: String,
    val sesionActual: Int, // Ej: 9
    val sesionTotal: Int   // Ej: 15
) {
    // Se calcula automáticamente el progreso de 0 a 100 para la barra
    val progreso: Int
        get() = if (sesionTotal > 0) (sesionActual * 100) / sesionTotal else 0

    // Genera el texto de la sesión solo con los números
    val textoSesion: String
        get() = "Sesión $sesionActual de $sesionTotal"

    // Genera el texto del porcentaje automáticamente
    val textoPorcentaje: String
        get() = "$progreso%"
}