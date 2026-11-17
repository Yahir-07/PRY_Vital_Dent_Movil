package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.radiobutton.MaterialRadioButton
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsRealizarPagoPresentador
import com.google.android.material.appbar.MaterialToolbar

class clsRealizarPagoVista : AppCompatActivity() {
    private lateinit var presentador: clsRealizarPagoPresentador
    private lateinit var tbRealizarPago: MaterialToolbar
    private lateinit var cvConcepto: MaterialCardView
    private lateinit var tvConceptoEtiqueta: TextView
    private lateinit var tvConcepto: TextView
    private lateinit var tvTotalEtiqueta: TextView
    private lateinit var tvTotal: TextView
    private lateinit var lblMedioPago: TextView
    private lateinit var cvTarjetaGuardada: MaterialCardView
    private lateinit var rbTarjetaGuardada: MaterialRadioButton
    private lateinit var ivMarcaTarjeta: ImageView
    private lateinit var tvTarjetaGuardada: TextView
    private lateinit var tvVenceTarjeta: TextView
    private lateinit var cvAgregarTarjeta: MaterialCardView
    private lateinit var rbAgregarTarjeta: MaterialRadioButton
    private lateinit var tvAvisoCifrado: TextView
    private lateinit var btnPagar: MaterialButton
    private lateinit var tvNotaHuella: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_pago)

        tbRealizarPago = findViewById(R.id.tbRealizarPago)
        cvConcepto = findViewById(R.id.cvConcepto)
        tvConceptoEtiqueta = findViewById(R.id.tvConceptoEtiqueta)
        tvConcepto = findViewById(R.id.tvConcepto)
        tvTotalEtiqueta = findViewById(R.id.tvTotalEtiqueta)
        tvTotal = findViewById(R.id.tvTotal)
        lblMedioPago = findViewById(R.id.lblMedioPago)
        cvTarjetaGuardada = findViewById(R.id.cvTarjetaGuardada)
        rbTarjetaGuardada = findViewById(R.id.rbTarjetaGuardada)
        ivMarcaTarjeta = findViewById(R.id.ivMarcaTarjeta)
        tvTarjetaGuardada = findViewById(R.id.tvTarjetaGuardada)
        tvVenceTarjeta = findViewById(R.id.tvVenceTarjeta)
        cvAgregarTarjeta = findViewById(R.id.cvAgregarTarjeta)
        rbAgregarTarjeta = findViewById(R.id.rbAgregarTarjeta)
        tvAvisoCifrado = findViewById(R.id.tvAvisoCifrado)
        btnPagar = findViewById(R.id.btnPagar)
        tvNotaHuella = findViewById(R.id.tvNotaHuella)

        presentador = clsRealizarPagoPresentador(this)
        presentador.CargarDatosPago()

        rbTarjetaGuardada.isChecked = true
        rbTarjetaGuardada.setOnClickListener {
            rbTarjetaGuardada.isChecked = true
            rbAgregarTarjeta.isChecked = false
        }

        rbAgregarTarjeta.setOnClickListener {
            rbAgregarTarjeta.isChecked = true
            rbTarjetaGuardada.isChecked = false
        }

        // Botón de Pagar
        btnPagar.setOnClickListener {
            val usaTarjetaGuardada = rbTarjetaGuardada.isChecked
            presentador.ClickPagar(usaTarjetaGuardada)
        }
    }

    fun MostrarDetalles(conceptoTexto: String, totalTexto: String) {
        tvConcepto.text = conceptoTexto
        tvTotal.text = totalTexto
        btnPagar.text = "Pagar $totalTexto"
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }


    fun IrAValidacionBiometrica() {
        // Reemplazamos el Toast por el Intent real hacia la Pantalla de Validación Biométrica
        val intent = Intent(this, clsValidacionBiometricaVista::class.java) // Asegúrate de usar el nombre exacto de tu Activity biométrica
        startActivity(intent)
    }
}