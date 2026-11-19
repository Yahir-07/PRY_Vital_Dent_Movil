package com.example.pry_vital_dent_movil.Vista

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsRealizarPagoPresentador
import com.google.android.material.appbar.MaterialToolbar

class clsRealizarPagoVista : AppCompatActivity() {

    companion object {
        // Texto que se muestra en la validación de huella (clsValidacionBiometricaVista)
        const val EXTRA_RESUMEN_PAGO = "RESUMEN_PAGO"
        private const val ESTADO_ESPERANDO_MP = "ESPERANDO_MERCADO_PAGO"
    }

    private lateinit var presentador: clsRealizarPagoPresentador
    private lateinit var tbRealizarPago: MaterialToolbar
    private lateinit var tilTratamientoPago: TextInputLayout
    private lateinit var actvTratamientoPago: MaterialAutoCompleteTextView
    private lateinit var cvConcepto: MaterialCardView
    private lateinit var tvConcepto: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tilMontoPago: TextInputLayout
    private lateinit var etMontoPago: TextInputEditText
    private lateinit var cvMercadoPago: MaterialCardView
    private lateinit var rbMercadoPago: MaterialRadioButton
    private lateinit var ivMercadoPago: ImageView
    private lateinit var tvAvisoCifrado: TextView
    private lateinit var btnPagar: MaterialButton
    private lateinit var tvNotaHuella: TextView

    private var textoBotonPagar = ""
    private var esperandoMercadoPago = false   // se abrió Mercado Pago y falta saber cómo terminó
    private var referenciaDelEnlace: String? = null

    // PAG-03 (Gustavo): la validación de huella regresa RESULT_OK si se aprobó
    private val validarHuella = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
        presentador.ResultadoValidacionBiometrica(resultado.resultCode == Activity.RESULT_OK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_pago)

        tbRealizarPago = findViewById(R.id.tbRealizarPago)
        tilTratamientoPago = findViewById(R.id.tilTratamientoPago)
        actvTratamientoPago = findViewById(R.id.actvTratamientoPago)
        cvConcepto = findViewById(R.id.cvConcepto)
        tvConcepto = findViewById(R.id.tvConcepto)
        tvTotal = findViewById(R.id.tvTotal)
        tilMontoPago = findViewById(R.id.tilMontoPago)
        etMontoPago = findViewById(R.id.etMontoPago)
        cvMercadoPago = findViewById(R.id.cvMercadoPago)
        rbMercadoPago = findViewById(R.id.rbMercadoPago)
        ivMercadoPago = findViewById(R.id.ivMercadoPago)
        tvAvisoCifrado = findViewById(R.id.tvAvisoCifrado)
        btnPagar = findViewById(R.id.btnPagar)
        tvNotaHuella = findViewById(R.id.tvNotaHuella)

        textoBotonPagar = getString(R.string.accion_pagar)
        esperandoMercadoPago = savedInstanceState?.getBoolean(ESTADO_ESPERANDO_MP) ?: false
        referenciaDelEnlace = LeerReferencia(intent)

        presentador = clsRealizarPagoPresentador(this)

        // Mercado Pago es el único medio (ahí el paciente elige tarjeta de crédito o débito)
        rbMercadoPago.isChecked = true
        rbMercadoPago.setOnClickListener { rbMercadoPago.isChecked = true }

        actvTratamientoPago.setOnItemClickListener { _, _, posicion, _ ->
            presentador.SeleccionarTratamiento(posicion)
        }

        etMontoPago.doAfterTextChanged { texto ->
            presentador.CambiarMonto(texto?.toString() ?: "")
        }

        btnPagar.setOnClickListener {
            presentador.ClickPagar(etMontoPago.text.toString().trim())
        }

        presentador.CargarDatosPago()
    }

    override fun onResume() {
        super.onResume()
        // Regresó de Mercado Pago (por el botón "Volver a Vital Dent" o con el botón Atrás)
        val referencia = referenciaDelEnlace
        if (referencia != null || esperandoMercadoPago) {
            referenciaDelEnlace = null
            presentador.AlVolverALaApp(referencia)
        }
    }

    // La pantalla ya estaba abierta y llega el enlace vitaldent://pago?referencia=...
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        referenciaDelEnlace = LeerReferencia(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(ESTADO_ESPERANDO_MP, esperandoMercadoPago)
    }

    private fun LeerReferencia(intent: Intent?): String? =
        intent?.data?.getQueryParameter("referencia")

    // ---------- Métodos que usa el presentador ----------

    fun MostrarOpciones(opciones: List<String>) {
        actvTratamientoPago.setSimpleItems(opciones.toTypedArray())
        if (opciones.isNotEmpty()) {
            actvTratamientoPago.setText(opciones[0], false)
        }
        btnPagar.isEnabled = true
        etMontoPago.isEnabled = true
    }

    fun MostrarSinSaldo() {
        tvConcepto.text = getString(R.string.pago_sin_saldo)
        tvTotal.text = "$0.00"
        etMontoPago.setText("")
        etMontoPago.isEnabled = false
        btnPagar.isEnabled = false
    }

    fun MostrarDetalles(conceptoTexto: String, saldoTexto: String) {
        tvConcepto.text = conceptoTexto
        tvTotal.text = saldoTexto
    }

    fun MostrarMontoSugerido(montoTexto: String, saldoTexto: String) {
        etMontoPago.setText(montoTexto)
        tilMontoPago.helperText = getString(R.string.ayuda_monto_maximo, saldoTexto)
        tilMontoPago.error = null
    }

    fun MostrarTotalBoton(totalTexto: String) {
        textoBotonPagar = if (totalTexto.isEmpty()) getString(R.string.accion_pagar)
        else getString(R.string.accion_pagar_monto, totalTexto)
        if (btnPagar.isEnabled) {
            btnPagar.text = textoBotonPagar
        }
    }

    fun MostrarErrorMonto(error: String?) {
        tilMontoPago.error = error
    }

    fun MostrarProcesando(procesando: Boolean) {
        btnPagar.isEnabled = !procesando
        btnPagar.text = if (procesando) "Conectando con Mercado Pago..." else textoBotonPagar
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun IrAValidacionBiometrica(resumenPago: String) {
        val intent = Intent(this, clsValidacionBiometricaVista::class.java)
        intent.putExtra(EXTRA_RESUMEN_PAGO, resumenPago)
        validarHuella.launch(intent)
    }

    // Abre la página segura de Mercado Pago en el navegador (ahí se escriben los datos de la tarjeta)
    fun AbrirMercadoPago(urlPago: String) {
        esperandoMercadoPago = true
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlPago)))
        } catch (e: ActivityNotFoundException) {
            esperandoMercadoPago = false
            MostrarMensaje("No hay un navegador para abrir Mercado Pago")
        }
    }

    fun IrAPagoAprobado(referencia: String) {
        esperandoMercadoPago = false
        val intent = Intent(this, clsPagoAprobadoVista::class.java)
        intent.putExtra(clsPagoAprobadoVista.EXTRA_REFERENCIA, referencia)
        startActivity(intent)
        finish()
    }
}
