package com.example.pry_vital_dent_movil.Vista

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.example.pry_vital_dent_movil.R
import com.example.pry_vital_dent_movil.Adaptador.DoctorCompactoAdaptador
import com.example.pry_vital_dent_movil.Modelo.DataDoctorCompacto
import com.example.pry_vital_dent_movil.Presentador.clsPerfilDoctorPresentador
import com.google.android.material.appbar.MaterialToolbar

class clsPerfilDoctorVista : AppCompatActivity() {
    private lateinit var presentador: clsPerfilDoctorPresentador
    private lateinit var tbPerfilDoctor: MaterialToolbar
    private lateinit var cvPerfilDoctor: MaterialCardView
    private lateinit var tvDoctorIniciales: TextView
    private lateinit var tvDoctorNombre: TextView
    private lateinit var tvDoctorEspecialidad: TextView
    private lateinit var tvDoctorClinica: TextView
    private lateinit var tvDoctorCedula: TextView
    private lateinit var tvDoctorDescripcion: TextView
    private lateinit var btnAgendarConDoctor: MaterialButton
    private lateinit var btnLlamarConsultorio: MaterialButton
    private lateinit var rvOtrosDoctores: RecyclerView
    private lateinit var doctorCompactoAdaptador: DoctorCompactoAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_doctor)

        tbPerfilDoctor = findViewById(R.id.tbPerfilDoctor)
        cvPerfilDoctor = findViewById(R.id.cvPerfilDoctor)
        tvDoctorIniciales = findViewById(R.id.tvDoctorIniciales)
        tvDoctorNombre = findViewById(R.id.tvDoctorNombre)
        tvDoctorEspecialidad = findViewById(R.id.tvDoctorEspecialidad)
        tvDoctorClinica = findViewById(R.id.tvDoctorClinica)
        tvDoctorCedula = findViewById(R.id.tvDoctorCedula)
        tvDoctorDescripcion = findViewById(R.id.tvDoctorDescripcion)
        btnAgendarConDoctor = findViewById(R.id.btnAgendarConDoctor)
        btnLlamarConsultorio = findViewById(R.id.btnLlamarConsultorio)
        rvOtrosDoctores = findViewById(R.id.rvOtrosDoctores)

        // 1. PRIMERO inicializamos el adaptador y lo asignamos al RecyclerView
        doctorCompactoAdaptador = DoctorCompactoAdaptador(emptyList()) { doctorSeleccionado ->
            presentador.SeleccionarOtroDoctor(doctorSeleccionado)
        }
        rvOtrosDoctores.layoutManager = LinearLayoutManager(this)
        rvOtrosDoctores.adapter = doctorCompactoAdaptador

        // 2. HASTA DESPUÉS inicializamos el presentador y cargamos los datos
        presentador = clsPerfilDoctorPresentador(this)
        presentador.CargarDatosDoctor()

        btnAgendarConDoctor.setOnClickListener {
            presentador.ClickAgendarCita()
        }

        btnLlamarConsultorio.setOnClickListener {
            presentador.ClickLlamarConsultorio()
        }
    }

    fun MostrarDetalles(iniciales: String, nombre: String, especialidad: String, clinica: String, cedula: String, descripcion: String) {
        tvDoctorIniciales.text = iniciales
        tvDoctorNombre.text = nombre
        tvDoctorEspecialidad.text = especialidad
        tvDoctorClinica.text = clinica
        tvDoctorCedula.text = cedula
        tvDoctorDescripcion.text = descripcion
    }

    fun MostrarOtrosDoctores(lista: List<DataDoctorCompacto>) {
        doctorCompactoAdaptador.actualizarLista(lista)
    }

    fun MostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun IrAAgendarCita() {
        Toast.makeText(this, "Abriendo flujo para agendar cita...", Toast.LENGTH_SHORT).show()
    }

    fun LlamarConsultorio() {
        Toast.makeText(this, "Iniciando llamada al consultorio...", Toast.LENGTH_SHORT).show()
    }
}