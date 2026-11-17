package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsValidacionBiometricaPresentador

class clsValidacionBiometricaVista : AppCompatActivity() {

    private lateinit var presentador: clsValidacionBiometricaPresentador
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

        presentador = clsValidacionBiometricaPresentador(this)

        // Texto de ejemplo para el concepto de confirmación
        tvConfirmarConcepto.text = "Endodoncia pieza 36 - $1,800.00"
        tvErrorHuella.text = "Coloca tu dedo en el sensor"

        // Simular escaneo al presionar el icono de la huella
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
        Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show()
    }

    fun IrAPagoAprobado() {
        // Aquí puedes abrir la Pantalla 16 (clsPagoAprobadoVista)
        Toast.makeText(this, "Redirigiendo a comprobante de pago...", Toast.LENGTH_SHORT).show()
    }

    fun CancelarYRegresar() {
        finish()
    }
}