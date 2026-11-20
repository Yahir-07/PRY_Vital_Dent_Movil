package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsValidacionBiometricaPresentador
import com.example.pry_vital_dent_movil.Modelo.clsBiometria

class clsValidacionBiometricaVista : AppCompatActivity() {

    private lateinit var presentador: clsValidacionBiometricaPresentador
    private lateinit var biometria: clsBiometria
    private lateinit var tvConfirmarConcepto: TextView
    private lateinit var ivHuella: ImageView
    private lateinit var tvErrorHuella: TextView
    private lateinit var btnCancelarPago: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_sheet_confirmar_pago)

        tvConfirmarConcepto = findViewById(R.id.tvConfirmarConcepto)
        ivHuella = findViewById(R.id.ivHuella)
        tvErrorHuella = findViewById(R.id.tvErrorHuella)
        btnCancelarPago = findViewById(R.id.btnCancelarPago)

        biometria = clsBiometria(this) // debe crearse aquí, en onCreate
        presentador = clsValidacionBiometricaPresentador(this)

        // Texto de ejemplo para el concepto de confirmación
        tvConfirmarConcepto.text = intent.getStringExtra(clsRealizarPagoVista.EXTRA_RESUMEN_PAGO)
            ?: "Endodoncia pieza 36 - $1,800.00"
        tvErrorHuella.text = "Coloca tu dedo en el sensor"

        // Al abrir la pantalla se pide la huella (post: espera a que se dibuje)
        if (savedInstanceState == null) {
            ivHuella.post { presentador.AlIniciar() }
        }

        // Volver a abrir el lector al tocar el ícono de la huella
        ivHuella.setOnClickListener {
            presentador.ClickEscanearHuella()
        }

        // Botón para cancelar el pago
        btnCancelarPago.setOnClickListener {
            presentador.ClickCancelarPago()
        }
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun MostrarError(mensajeError: String) {
        tvErrorHuella.text = mensajeError
        tvErrorHuella.visibility = View.VISIBLE
        Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show()
    }

    fun MostrarAviso(texto: String) {
        tvErrorHuella.text = texto
        tvErrorHuella.visibility = View.VISIBLE
    }

    // Lector real de huella o rostro (AndroidX Biometric)
    fun SolicitarHuella(mensajeSinIntentos: String) {
        biometria.Autenticar(
            titulo = "Confirma tu pago",
            subtitulo = tvConfirmarConcepto.text.toString(),
            alExito = { presentador.HuellaReconocida() },
            alError = { mensaje -> presentador.HuellaNoValidada(mensaje) },
            alCancelar = { presentador.LectorCancelado() },
            alIntentoFallido = { restantes -> presentador.IntentoFallido(restantes) },
            mensajeSinIntentos = mensajeSinIntentos
        )
    }

    fun IrAPagoAprobado() {
        // Huella aprobada: Realizar pago continúa con Mercado Pago y después muestra la Pantalla 16
        setResult(RESULT_OK)
        finish()
    }

    fun CancelarYRegresar() {
        setResult(RESULT_CANCELED)
        finish()
    }
}