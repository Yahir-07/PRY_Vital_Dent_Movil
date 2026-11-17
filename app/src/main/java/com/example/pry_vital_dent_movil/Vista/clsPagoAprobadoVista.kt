package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsPagoAprobadoPresentador

class clsPagoAprobadoVista : AppCompatActivity() {

    private lateinit var presentador: clsPagoAprobadoPresentador
    private lateinit var ivExito: ImageView
    private lateinit var tvTituloExito: TextView
    private lateinit var tvMontoPagado: TextView
    private lateinit var cvComprobante: MaterialCardView
    private lateinit var tvConceptoEtiqueta: TextView
    private lateinit var tvConceptoValor: TextView
    private lateinit var tvFechaEtiqueta: TextView
    private lateinit var tvFechaValor: TextView
    private lateinit var tvMedioEtiqueta: TextView
    private lateinit var tvMedioValor: TextView
    private lateinit var tvAutorizadoEtiqueta: TextView
    private lateinit var tvAutorizadoValor: TextView
    private lateinit var tvFolioEtiqueta: TextView
    private lateinit var tvFolioValor: TextView
    private lateinit var btnVerMisPagos: MaterialButton
    private lateinit var btnVolverInicio: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago_aprobado)

        ivExito = findViewById(R.id.ivExito)
        tvTituloExito = findViewById(R.id.tvTituloExito)
        tvMontoPagado = findViewById(R.id.tvMontoPagado)
        cvComprobante = findViewById(R.id.cvComprobante)
        tvConceptoEtiqueta = findViewById(R.id.tvConceptoEtiqueta)
        tvConceptoValor = findViewById(R.id.tvConceptoValor)
        tvFechaEtiqueta = findViewById(R.id.tvFechaEtiqueta)
        tvFechaValor = findViewById(R.id.tvFechaValor)
        tvMedioEtiqueta = findViewById(R.id.tvMedioEtiqueta)
        tvMedioValor = findViewById(R.id.tvMedioValor)
        tvAutorizadoEtiqueta = findViewById(R.id.tvAutorizadoEtiqueta)
        tvAutorizadoValor = findViewById(R.id.tvAutorizadoValor)
        tvFolioEtiqueta = findViewById(R.id.tvFolioEtiqueta)
        tvFolioValor = findViewById(R.id.tvFolioValor)
        btnVerMisPagos = findViewById(R.id.btnVerMisPagos)
        btnVolverInicio = findViewById(R.id.btnVolverInicio)

        presentador = clsPagoAprobadoPresentador(this)
        presentador.CargarComprobante()

        btnVerMisPagos.setOnClickListener {
            presentador.ClickVerMisPagos()
        }

        btnVolverInicio.setOnClickListener {
            presentador.ClickVolverInicio()
        }
    }

    fun MostrarDatosComprobante(monto: String, concepto: String, fecha: String, medio: String, autorizado: String, folio: String) {
        tvMontoPagado.text = monto
        tvConceptoValor.text = concepto
        tvFechaValor.text = fecha
        tvMedioValor.text = medio
        tvAutorizadoValor.text = autorizado
        tvFolioValor.text = folio
    }

    fun IrAMisPagos() {
        // Redirige de regreso al historial de pagos (Pantalla 13)
        val intent = Intent(this, clsPagosVista::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    fun IrAInicio() {
        // Redirige al panel principal / inicio
        val intent = Intent(this, clsInicioVista::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}