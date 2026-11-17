package com.example.pry_vital_dent_movil.Modelo

data class DataAvance(
    val idAvance: String,
    val sesion: String,      // Ej: "Sesión 10"
    val fecha: String,       // Ej: "15/10/2026"
    val descripcion: String  // Ej: "Ajuste de brackets y cambio de ligas."
)