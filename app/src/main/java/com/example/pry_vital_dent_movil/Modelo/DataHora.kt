package com.example.pry_vital_dent_movil.Modelo

data class DataHora(
    val hora: String,            // Ej: "10:00 a. m." (lo que se muestra)
    var isDisponible: Boolean,   // true = libre (enabled), false = ocupada (disabled con tachado)
    var isSeleccionada: Boolean = false, // true = seleccionada visualmente
    val valor: String = ""       // Ej: "10:00" (lo que se envía a agendar_cita.php)
)
