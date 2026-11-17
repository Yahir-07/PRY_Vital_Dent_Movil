package com.example.pry_vital_dent_movil.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_vital_dent_movil.Modelo.DataDoctor
import com.example.pry_vital_dent_movil.R

class DoctorAdaptador(
    private var listaDoctores: List<DataDoctor>,
    private val onSeleccionarDoctor: (DataDoctor) -> Unit
) : RecyclerView.Adapter<DoctorAdaptador.DoctorViewHolder>() {

    class DoctorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDoctorIniciales: TextView = view.findViewById(R.id.tvDoctorIniciales)
        val tvDoctorNombre: TextView = view.findViewById(R.id.tvDoctorNombre)
        val tvDoctorEspecialidad: TextView = view.findViewById(R.id.tvDoctorEspecialidad)
        val tvDoctorDescripcion: TextView = view.findViewById(R.id.tvDoctorDescripcion)
        val tvDoctorCedula: TextView = view.findViewById(R.id.tvDoctorCedula)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = listaDoctores[position]

        holder.tvDoctorIniciales.text = doctor.iniciales
        holder.tvDoctorNombre.text = doctor.nombre
        holder.tvDoctorEspecialidad.text = doctor.especialidad
        holder.tvDoctorDescripcion.text = doctor.descripcion
        holder.tvDoctorCedula.text = "Cédula profesional ${doctor.cedula}"

        // Clic en la tarjeta completa para ir al perfil
        holder.itemView.setOnClickListener {
            onSeleccionarDoctor(doctor)
        }
    }

    override fun getItemCount(): Int = listaDoctores.size

    fun actualizarLista(nuevaLista: List<DataDoctor>) {
        listaDoctores = nuevaLista
        notifyDataSetChanged()
    }
}