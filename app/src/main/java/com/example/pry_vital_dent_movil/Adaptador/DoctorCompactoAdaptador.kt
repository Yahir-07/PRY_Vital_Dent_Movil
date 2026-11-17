package com.example.pry_vital_dent_movil.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_vital_dent_movil.Modelo.DataDoctorCompacto
import com.example.pry_vital_dent_movil.R

class DoctorCompactoAdaptador(
    private var listaDoctores: List<DataDoctorCompacto>,
    private val onSeleccionarDoctor: (DataDoctorCompacto) -> Unit
) : RecyclerView.Adapter<DoctorCompactoAdaptador.DoctorCompactoViewHolder>() {

    class DoctorCompactoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDoctorIniciales: TextView = view.findViewById(R.id.tvDoctorIniciales)
        val tvDoctorNombre: TextView = view.findViewById(R.id.tvDoctorNombre)
        val tvDoctorEspecialidad: TextView = view.findViewById(R.id.tvDoctorEspecialidad)
        val ivDoctorIr: ImageView = view.findViewById(R.id.ivDoctorIr)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorCompactoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doctor_compacto, parent, false)
        return DoctorCompactoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorCompactoViewHolder, position: Int) {
        val doctor = listaDoctores[position]

        holder.tvDoctorIniciales.text = doctor.iniciales
        holder.tvDoctorNombre.text = doctor.nombre
        holder.tvDoctorEspecialidad.text = doctor.especialidad

        holder.itemView.setOnClickListener {
            onSeleccionarDoctor(doctor)
        }
    }

    override fun getItemCount(): Int = listaDoctores.size

    fun actualizarLista(nuevaLista: List<DataDoctorCompacto>) {
        listaDoctores = nuevaLista
        notifyDataSetChanged()
    }
}