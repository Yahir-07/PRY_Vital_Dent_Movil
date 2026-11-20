package com.example.pry_vital_dent_movil.Modelo

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

/**
 * Lector de huella o rostro del celular (AndroidX Biometric).
 * La app nunca ve la huella: Android solo responde si es el dueño del celular o no.
 *
 * IMPORTANTE: se crea en onCreate() de la Activity:  biometria = clsBiometria(this)
 */
class clsBiometria(private val actividad: FragmentActivity) {

    companion object {
        // BIOMETRIC_WEAK acepta huella y también el reconocimiento facial de la mayoría de los celulares
        private const val AUTENTICADORES = BIOMETRIC_WEAK

        // Intentos fallidos permitidos antes de pedir la contraseña
        const val MAX_INTENTOS = 3
    }

    // Lo que se hace en el intento actual (cambia en cada llamada a Autenticar)
    private var alExito: () -> Unit = {}
    private var alError: (String) -> Unit = {}
    private var alCancelar: () -> Unit = {}
    private var alIntentoFallido: (Int) -> Unit = {}
    private var mensajeSinIntentos = ""
    private var intentosFallidos = 0

    // Se crea en init (o sea, dentro del onCreate de la Activity), como lo pide Android
    private lateinit var lector: BiometricPrompt

    init {
        lector = BiometricPrompt(
            actividad,
            ContextCompat.getMainExecutor(actividad),
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    alExito()
                }

                // Huella o rostro no reconocido; el usuario puede volver a intentar
                override fun onAuthenticationFailed() {
                    intentosFallidos++
                    if (intentosFallidos >= MAX_INTENTOS) {
                        lector.cancelAuthentication()
                        alError(mensajeSinIntentos)
                    } else {
                        alIntentoFallido(MAX_INTENTOS - intentosFallidos)
                    }
                }

                override fun onAuthenticationError(codigo: Int, mensaje: CharSequence) {
                    when (codigo) {
                        // El usuario tocó "Cancelar" o cerró el lector: no es un error
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON,
                        BiometricPrompt.ERROR_USER_CANCELED -> alCancelar()

                        // Lo cancelamos nosotros al llegar a MAX_INTENTOS (ya se avisó)
                        BiometricPrompt.ERROR_CANCELED ->
                            if (intentosFallidos < MAX_INTENTOS) alCancelar()

                        BiometricPrompt.ERROR_LOCKOUT,
                        BiometricPrompt.ERROR_LOCKOUT_PERMANENT ->
                            alError("El lector se bloqueó por muchos intentos. Desbloquea tu celular con tu PIN e inténtalo de nuevo.")

                        else -> alError(mensaje.toString())
                    }
                }
            }
        )
    }

    /** true si el celular tiene sensor y el usuario ya registró una huella o rostro. */
    fun EstaDisponible(): Boolean =
        BiometricManager.from(actividad).canAuthenticate(AUTENTICADORES) == BiometricManager.BIOMETRIC_SUCCESS

    /** Texto para explicar por qué no se puede usar la huella. */
    fun MotivoNoDisponible(): String =
        when (BiometricManager.from(actividad).canAuthenticate(AUTENTICADORES)) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                "Este celular no tiene lector de huella ni reconocimiento facial."
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                "No tienes una huella o rostro registrado. Agrégalo en Ajustes > Seguridad de tu celular."
            else ->
                "El lector de huella no está disponible en este momento. Intenta más tarde."
        }

    /**
     * Muestra el lector del sistema (huella o rostro).
     * alIntentoFallido recibe cuántos intentos le quedan al usuario.
     * mensajeSinIntentos: lo que se muestra al llegar a MAX_INTENTOS.
     */
    fun Autenticar(
        titulo: String,
        subtitulo: String,
        alExito: () -> Unit,
        alError: (String) -> Unit,
        alCancelar: () -> Unit = {},
        alIntentoFallido: (Int) -> Unit = {},
        mensajeSinIntentos: String = "Demasiados intentos. Ingresa con tu correo y contraseña."
    ) {
        if (!EstaDisponible()) {
            alError(MotivoNoDisponible())
            return
        }

        this.alExito = alExito
        this.alError = alError
        this.alCancelar = alCancelar
        this.alIntentoFallido = alIntentoFallido
        this.mensajeSinIntentos = mensajeSinIntentos
        intentosFallidos = 0

        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle(titulo)
            .setSubtitle(subtitulo)
            .setNegativeButtonText("Cancelar")
            .setAllowedAuthenticators(AUTENTICADORES)
            .setConfirmationRequired(false) // con rostro entra directo, sin tocar "Confirmar"
            .build()

        lector.authenticate(info)
    }
}
