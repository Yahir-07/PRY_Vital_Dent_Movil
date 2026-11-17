package com.example.pry_vital_dent_movil.Modelo

data class DataHora(
    val hora: String,            // Ej: "10:00 AM"
    var isDisponible: Boolean,   // true = libre (enabled), false = ocupada (disabled con tachado)
    var isSeleccionada: Boolean = false // true = seleccionada visualmente
)