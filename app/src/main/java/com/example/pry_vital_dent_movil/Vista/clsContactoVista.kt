package com.example.pry_vital_dent_movil.Vista

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Presentador.clsContactoPresentador
import com.google.android.material.appbar.MaterialToolbar
import android.content.Intent
import android.net.Uri

class clsContactoVista : AppCompatActivity() {
    private lateinit var presentador: clsContactoPresentador
    private lateinit var tbContactoHorario: MaterialToolbar
    private lateinit var cvDireccion: MaterialCardView
    private lateinit var tvDireccionValor: TextView
    private lateinit var btnComoLlegar: MaterialButton
    private lateinit var cvTelefono: MaterialCardView
    private lateinit var tvTelefonoValor: TextView
    private lateinit var btnLlamarConsultorio: MaterialButton
    private lateinit var cvHorario: MaterialCardView
    private lateinit var tvHorarioSemana: TextView
    private lateinit var tvHorarioSabado: TextView
    private lateinit var tvHorarioDomingo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacto_horario)

        tbContactoHorario = findViewById(R.id.tbContactoHorario)
        cvDireccion = findViewById(R.id.cvDireccion)
        tvDireccionValor = findViewById(R.id.tvDireccionValor)
        btnComoLlegar = findViewById(R.id.btnComoLlegar)
        cvTelefono = findViewById(R.id.cvTelefono)
        tvTelefonoValor = findViewById(R.id.tvTelefonoValor)
        btnLlamarConsultorio = findViewById(R.id.btnLlamarConsultorio)
        cvHorario = findViewById(R.id.cvHorario)
        tvHorarioSemana = findViewById(R.id.tvHorarioSemana)
        tvHorarioSabado = findViewById(R.id.tvHorarioSabado)
        tvHorarioDomingo = findViewById(R.id.tvHorarioDomingo)

        presentador = clsContactoPresentador(this)
        presentador.CargarInformacion()

        btnComoLlegar.setOnClickListener {
            presentador.ClickComoLlegar()
        }

        btnLlamarConsultorio.setOnClickListener {
            presentador.ClickLlamar()
        }
    }

    fun MostrarInformacion(direccion: String, telefono: String, sem: String, sab: String, dom: String) {
        tvDireccionValor.text = direccion
        tvTelefonoValor.text = telefono
        tvHorarioSemana.text = sem
        tvHorarioSabado.text = sab
        tvHorarioDomingo.text = dom
    }

    fun AbrirMapaComoLlegar() {
        val direccion = "Av. Juarez 120, Juárez, 43000 Huejutla de Reyes, Hgo."

        val uri = Uri.Builder()
            .scheme("https")
            .authority("www.google.com")
            .path("maps/dir/")
            .appendQueryParameter("api", "1")
            .appendQueryParameter("destination", direccion)
            .appendQueryParameter("travelmode", "driving")
            .build()

        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    fun RealizarLlamadaConsultorio() {
        Toast.makeText(this, "Iniciando llamada...", Toast.LENGTH_SHORT).show()
        // const intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:7769961020"))
        // startActivity(intent)
    }
}