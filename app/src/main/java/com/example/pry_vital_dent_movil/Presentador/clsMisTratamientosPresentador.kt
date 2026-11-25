package com.example.pry_vital_dent_movil.Presentador

import com.example.pry_vital_dent_movil.Modelo.DataTratamiento
import com.example.pry_vital_dent_movil.Modelo.clsMisTratamientosModelo
import com.example.pry_vital_dent_movil.Vista.clsMisTratamientosVista

class clsMisTratamientosPresentador(private val vista: clsMisTratamientosVista) {

    private val modelo = clsMisTratamientosModelo(vista)

    // false = todavía no abrimos Tipos de brackets, así solo se abre sola una vez
    private var yaSeAbrieronBrackets = false

    fun CargarTratamientosEnCurso() {
        modelo.ObtenerTratamientosEnCurso { exito, mensaje, lista ->
            vista.MostrarListaTratamientos(lista)
            vista.MostrarAviso(
                when {
                    !exito -> mensaje
                    lista.isEmpty() -> "No tienes tratamientos en curso. Conoce los tipos de brackets y solicita una valoración."
                    else -> "Toca un tratamiento para ver su avance sesión por sesión."
                }
            )

            // Si la API respondió bien pero la lista viene vacía, el paciente no tiene tratamiento
            val sinTratamiento = exito && lista.isEmpty()
            vista.MostrarBotonBrackets(sinTratamiento)

            // Lo mandamos a Tipos de brackets (como dijo Gustavo), pero solo la primera vez
            if (sinTratamiento && !yaSeAbrieronBrackets) {
                yaSeAbrieronBrackets = true
                vista.IrATiposBrackets()
            }
        }
    }

    fun CargarTratamientosFinalizados() {
        modelo.ObtenerTratamientosFinalizados { exito, mensaje, lista ->
            vista.MostrarListaTratamientos(lista)
            vista.MostrarBotonBrackets(false) // en Finalizados no va el botón
            vista.MostrarAviso(
                when {
                    !exito -> mensaje
                    lista.isEmpty() -> "Aún no tienes tratamientos finalizados."
                    else -> "Tratamientos completados con éxito."
                }
            )
        }
    }

    fun ClickTratamiento(tratamiento: DataTratamiento) {
        vista.IrADetalleTratamiento(tratamiento.idTratamiento)
    }

    fun ClickVerTiposBrackets() {
        vista.IrATiposBrackets()
    }
}
