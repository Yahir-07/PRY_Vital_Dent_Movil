package com.example.pry_vital_dent_movil.Modelo

// Guarda en memoria lo que se captura en Crear cuenta y en los cuestionarios 1, 2 y 3,
// para mandarlo tod0 junto a guardar_cuestionario.php al finalizar.
// Se limpia en cuanto el servidor guarda la ficha (la contraseña no se queda en memoria).
object clsRegistroTemporal {

    // Cuenta recién creada en registro.php (el servidor la vuelve a comprobar)
    var correo = ""
    var contrasena = ""

    // Cuestionario 1 (datos personales), ya en el formato de la tabla pacientes
    var nombre = ""
    var telefono = ""
    var fechaNacimiento = ""   // AAAA-MM-DD
    var sexo = ""              // "F", "M" o "" (= NULL)
    var domicilio = ""

    // Cuestionario 2 (datos clínicos)
    var tipoSangre = ""
    var sangrado = "no"        // "si" o "no"
    var enfermedades = ""
    var medicamentos = ""
    var motivoConsulta = ""
    var observaciones = ""     // alergia y antecedentes odontológicos

    fun Iniciar(correo: String, contrasena: String) {
        Limpiar()
        this.correo = correo
        this.contrasena = contrasena
    }

    fun HayCuenta(): Boolean = correo.isNotEmpty() && contrasena.isNotEmpty()

    fun Limpiar() {
        correo = ""; contrasena = ""
        nombre = ""; telefono = ""; fechaNacimiento = ""; sexo = ""; domicilio = ""
        tipoSangre = ""; sangrado = "no"; enfermedades = ""; medicamentos = ""
        motivoConsulta = ""; observaciones = ""
    }
}