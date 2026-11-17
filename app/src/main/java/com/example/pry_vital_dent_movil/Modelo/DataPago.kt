package com.example.pry_vital_dent_movil.Modelo

data class DataPago(
    val idPago: String,
    val concepto: String,    // Ej: "Ortodoncia · mensualidad"
    val detalle: String,     // Ej: "24 oct 2026 · $950.00"
    val estado: String       // Ej: "Aprobado", "Rechazado", "Pendiente"
)