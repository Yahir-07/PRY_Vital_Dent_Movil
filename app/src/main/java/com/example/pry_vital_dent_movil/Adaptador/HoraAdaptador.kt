package com.example.pry_vital_dent_movil.Adaptador

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_vital_dent_movil.Modelo.DataHora
import com.example.pry_vital_dent_movil.R

class HoraAdaptador(
    private var listaHoras: List<DataHora>,
    private val onSeleccionarHora: (DataHora) -> Unit
) : RecyclerView.Adapter<HoraAdaptador.HoraViewHolder>() {

    class HoraViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvHora: TextView = view.findViewById(R.id.tvHora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoraViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hora, parent, false)
        return HoraViewHolder(view)
    }

    override fun onBindViewHolder(holder: HoraViewHolder, position: Int) {
        val item = listaHoras[position]

        holder.tvHora.text = item.hora
        holder.tvHora.isEnabled = item.isDisponible
        holder.tvHora.isSelected = item.isSeleccionada

        // Aplicar tachado si está ocupada (no disponible)
        if (!item.isDisponible) {
            holder.tvHora.paintFlags = holder.tvHora.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.tvHora.paintFlags = holder.tvHora.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        // Clic en la hora (solo si está disponible)
        holder.itemView.setOnClickListener {
            if (item.isDisponible) {
                // Marcar la seleccionada y desmarcar las demás
                listaHoras.forEach { it.isSeleccionada = false }
                item.isSeleccionada = true
                notifyDataSetChanged()

                onSeleccionarHora(item)
            }
        }
    }

    override fun getItemCount(): Int = listaHoras.size

    fun actualizarLista(nuevaLista: List<DataHora>) {
        listaHoras = nuevaLista
        notifyDataSetChanged()
    }
}