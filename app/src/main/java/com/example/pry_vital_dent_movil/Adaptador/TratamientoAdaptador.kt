package com.example.pry_vital_dent_movil.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_vital_dent_movil.Modelo.DataTratamiento
import com.example.pry_vital_dent_movil.R
import com.google.android.material.progressindicator.LinearProgressIndicator

class TratamientoAdaptador(
    private var listaTratamientos: List<DataTratamiento>,
    private val onSeleccionarTratamiento: (DataTratamiento) -> Unit
) : RecyclerView.Adapter<TratamientoAdaptador.TratamientoViewHolder>() {

    class TratamientoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTratamientoNombre: TextView = view.findViewById(R.id.tvTratamientoNombre)
        val tvTratamientoDetalle: TextView = view.findViewById(R.id.tvTratamientoDetalle)
        val tvTratamientoEstado: TextView = view.findViewById(R.id.tvTratamientoEstado)
        val pbTratamiento: LinearProgressIndicator = view.findViewById(R.id.pbTratamiento)
        val tvTratamientoSesion: TextView = view.findViewById(R.id.tvTratamientoSesion)
        val tvTratamientoPorcentaje: TextView = view.findViewById(R.id.tvTratamientoPorcentaje)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TratamientoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tratamiento, parent, false)
        return TratamientoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TratamientoViewHolder, position: Int) {
        val tratamiento = listaTratamientos[position]

        holder.tvTratamientoNombre.text = tratamiento.nombre
        holder.tvTratamientoDetalle.text = tratamiento.detalle
        holder.tvTratamientoEstado.text = tratamiento.estado
        holder.pbTratamiento.progress = tratamiento.progreso
        holder.tvTratamientoSesion.text = tratamiento.textoSesion
        holder.tvTratamientoPorcentaje.text = tratamiento.textoPorcentaje

        holder.itemView.setOnClickListener {
            onSeleccionarTratamiento(tratamiento)
        }
    }

    override fun getItemCount(): Int = listaTratamientos.size

    fun actualizarLista(nuevaLista: List<DataTratamiento>) {
        listaTratamientos = nuevaLista
        notifyDataSetChanged()
    }
}