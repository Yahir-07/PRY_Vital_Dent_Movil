package com.example.pry_vital_dent_movil.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_vital_dent_movil.Modelo.DataHistorial
import com.example.pry_vital_dent_movil.R

class HistorialAdaptador(
    private var listaHistorial: List<DataHistorial>,
    private val onSeleccionarHistorial: (DataHistorial) -> Unit
) : RecyclerView.Adapter<HistorialAdaptador.HistorialViewHolder>() {

    class HistorialViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivHistorialIcono: ImageView = view.findViewById(R.id.ivHistorialIcono)
        val tvHistorialTitulo: TextView = view.findViewById(R.id.tvHistorialTitulo)
        val tvHistorialDetalle: TextView = view.findViewById(R.id.tvHistorialDetalle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historial, parent, false)
        return HistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        val historial = listaHistorial[position]

        holder.tvHistorialTitulo.text = historial.titulo
        holder.tvHistorialDetalle.text = historial.detalle

        holder.itemView.setOnClickListener {
            onSeleccionarHistorial(historial)
        }
    }

    override fun getItemCount(): Int = listaHistorial.size

    fun actualizarLista(nuevaLista: List<DataHistorial>) {
        listaHistorial = nuevaLista
        notifyDataSetChanged()
    }
}