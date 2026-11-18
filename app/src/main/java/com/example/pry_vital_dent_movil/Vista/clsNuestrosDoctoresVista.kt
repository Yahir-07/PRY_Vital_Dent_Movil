package com.example.pry_vital_dent_movil.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Adaptador.DoctorAdaptador
import com.example.pry_vital_dent_movil.Modelo.DataDoctor
import com.example.pry_vital_dent_movil.Presentador.clsNuestrosDoctoresPresentador

class clsNuestrosDoctoresVista : AppCompatActivity() {

    private lateinit var presentador: clsNuestrosDoctoresPresentador
    private lateinit var adaptador: DoctorAdaptador

    private lateinit var tbDoctores: MaterialToolbar
    private lateinit var tvEquipoMedico: TextView
    private lateinit var tvDoctoresDescripcion: TextView
    private lateinit var rvDoctores: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctores)

        tbDoctores = findViewById(R.id.tbDoctores)
        tvEquipoMedico = findViewById(R.id.tvEquipoMedico)
        tvDoctoresDescripcion = findViewById(R.id.tvDoctoresDescripcion)
        rvDoctores = findViewById(R.id.rvDoctores)

        tvEquipoMedico.text = "EQUIPO MÉDICO"
        tvDoctoresDescripcion.text = "Conoce a los especialistas que cuidan tu sonrisa. Toca un doctor para ver su perfil."

        tbDoctores.setNavigationOnClickListener { finish() }

        // Configurar RecyclerView con su adaptador
        adaptador = DoctorAdaptador(emptyList()) { doctorSeleccionado ->
            presentador.SeleccionarDoctor(doctorSeleccionado.idDoctor)
        }
        rvDoctores.layoutManager = LinearLayoutManager(this)
        rvDoctores.adapter = adaptador

        presentador = clsNuestrosDoctoresPresentador(this)
        presentador.CargarDoctores()
    }

    fun MostrarListaDoctores(lista: List<DataDoctor>) {
        adaptador.actualizarLista(lista)
    }

    fun IrAPerfilDoctor(idDoctor: String) {
        val intent = Intent(this, clsPerfilDoctorVista::class.java)
        intent.putExtra(clsPerfilDoctorVista.EXTRA_ID_DOCTOR, idDoctor)
        startActivity(intent)
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}