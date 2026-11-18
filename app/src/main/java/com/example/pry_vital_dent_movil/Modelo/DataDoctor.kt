package com.example.pry_vital_dent_movil.Modelo

data class DataDoctor(
    val iniciales: String,
    val nombre: String,
    val especialidad: String,
    val descripcion: String,
    val cedula: String,
    val idDoctor: Long = 0
)
