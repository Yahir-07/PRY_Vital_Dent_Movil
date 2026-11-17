package com.example.pry_vital_dent_movil.Modelo

data class DataDoctorCompacto(
    val idDoctor: String,
    val iniciales: String,     // Ej: "LM" o "ST"
    val nombre: String,        // Ej: "Dr. Luis Méndez"
    val especialidad: String   // Ej: "Implantologia"
)