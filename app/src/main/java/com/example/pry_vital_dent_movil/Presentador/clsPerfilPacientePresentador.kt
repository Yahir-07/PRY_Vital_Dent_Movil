package com.example.pry_vital_dent_movil.Presentador

import android.graphics.Bitmap
import android.net.Uri
import com.example.pry_vital_dent_movil.Modelo.DatosPerfil
import com.example.pry_vital_dent_movil.Modelo.clsCamaraModelo
import com.example.pry_vital_dent_movil.Modelo.clsPerfilPacienteModelo
import com.example.pry_vital_dent_movil.Vista.clsPerfilPacienteVista

class clsPerfilPacientePresentador(private val vista: clsPerfilPacienteVista) {

    private val modelo = clsPerfilPacienteModelo(vista)
    private val camara = clsCamaraModelo(vista)
    private var subiendoFoto = false

    // Se guardan para mandarlos a Editar
    private var datosActuales: DatosPerfil? = null

    fun CargarPerfil() {
        RecargarDatos()
        camara.ObtenerFoto { foto ->
            if (foto != null) vista.MostrarFoto(foto)
        }
    }

    // También se llama al regresar de Editar
    fun RecargarDatos() {
        modelo.ObtenerDatosPerfil { datos, mensaje, huellaActiva ->
            if (datos != null) {
                datosActuales = datos
                vista.MostrarInformacion(datos.telefono, datos.correo, datos.domicilio, huellaActiva)
                vista.OcultarAviso()
            } else {
                vista.MostrarAvisoError(mensaje)
            }
        }
    }

    // ---------- Editar datos de contacto (HIS-02) ----------

    fun ClickEditarContacto() {
        val datos = datosActuales
        if (datos == null) {
            vista.MostrarMensaje("Espera a que se carguen tus datos")
            return
        }
        vista.IrAEditarContacto(datos.telefono, datos.correo, datos.domicilio)
    }

    // ---------- Cámara: foto de perfil (ACC-08) ----------

    fun ClickTomarFoto() {
        if (subiendoFoto) return
        when {
            !vista.TieneCamara() ->
                vista.MostrarMensaje("Este celular no tiene cámara")
            vista.PermisoCamaraConcedido() ->
                vista.AbrirCamara(camara.CrearArchivoParaFoto())
            // Ya lo negó una vez: se explica para qué es antes de volver a pedirlo
            vista.DebeExplicarPermisoCamara() ->
                vista.MostrarExplicacionPermisoCamara()
            else ->
                vista.PedirPermisoCamara()
        }
    }

    fun PermisoExplicadoAceptado() {
        vista.PedirPermisoCamara()
    }

    fun ResultadoPermisoCamara(concedido: Boolean) {
        when {
            concedido -> vista.AbrirCamara(camara.CrearArchivoParaFoto())
            // Marcó "No volver a preguntar": solo se puede activar desde Ajustes
            !vista.DebeExplicarPermisoCamara() -> vista.MostrarDialogoAjustesCamara()
            else -> vista.MostrarMensaje("Sin permiso de cámara no se puede tomar la foto")
        }
    }

    fun ResultadoFotoTomada(tomada: Boolean, uri: Uri?) {
        if (!tomada || uri == null) return // canceló la cámara: no se cambia nada
        subiendoFoto = true
        vista.MostrarSubiendoFoto(true)
        camara.PrepararFoto(uri) { foto ->
            if (foto == null) {
                TerminarSubida()
                vista.MostrarMensaje("No se pudo leer la foto. Inténtalo de nuevo.")
                return@PrepararFoto
            }
            GuardarFoto(foto)
        }
    }

    private fun GuardarFoto(foto: Bitmap) {
        camara.GuardarFoto(foto) { exito, mensaje ->
            TerminarSubida()
            if (exito) {
                vista.MostrarFoto(foto)
                vista.MostrarConfirmacion("Foto de perfil actualizada")
            } else {
                vista.MostrarMensaje(mensaje)
            }
        }
    }

    private fun TerminarSubida() {
        subiendoFoto = false
        vista.MostrarSubiendoFoto(false)
    }

    fun CambiarEstadoHuella(activada: Boolean) {
        if (!activada) {
            GuardarEstadoHuella(false)
            return
        }
        // Para activarla, el celular debe tener huella o rostro y el paciente debe confirmarla
        if (!vista.BiometriaDisponible()) {
            vista.MarcarSwitchHuella(false)
            vista.MostrarMensaje(vista.MotivoBiometriaNoDisponible())
            return
        }
        vista.ConfirmarHuellaParaActivar()
    }

    fun HuellaPagosConfirmada() {
        GuardarEstadoHuella(true)
    }

    fun HuellaPagosNoConfirmada(mensaje: String?) {
        vista.MarcarSwitchHuella(false)
        if (mensaje != null) vista.MostrarMensaje(mensaje)
    }

    private fun GuardarEstadoHuella(activada: Boolean) {
        modelo.ActualizarEstadoHuella(activada) { exito ->
            if (exito) {
                vista.MostrarConfirmacion(if (activada) "Huella para pagos activada" else "Huella para pagos desactivada")
            }
        }
    }

    fun ClickCambiarContrasena() {
        vista.IrACambiarContrasena() // Pantalla 18
    }

    fun ClickNuestrosDoctores() {
        vista.IrANuestrosDoctores() // Pantalla 20
    }

    fun ClickContactoHorario() {
        vista.IrAContactoHorario() // Pantalla 22
    }

    fun ClickCerrarSesion() {
        modelo.CerrarSesion()
        vista.CerrarSesionApp()
    }
}