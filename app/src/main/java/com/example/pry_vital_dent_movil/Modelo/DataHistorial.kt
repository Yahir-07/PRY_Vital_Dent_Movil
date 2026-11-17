package com.example.pry_vital_dent_movil.Modelo

data class DataHistorial(
    val idHistorial: String,
    val titulo: String,    // Ej: "Cita atendida", "Consulta", "Receta"
    val detalle: String    // Ej: "24 oct 2026 - Ajuste de brackets"
)