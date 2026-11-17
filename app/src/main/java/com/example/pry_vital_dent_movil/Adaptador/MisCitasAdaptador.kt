package com.example.pry_vital_dent_movil.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pry_vital_dent_movil.Modelo.DataCita
import com.example.pry_vital_dent_movil.R

class MisCitasAdaptador(
    private var listaCitas: List<DataCita>,
    private val onSeleccionarCita: (DataCita) -> Unit
) : RecyclerView.Adapter<MisCitasAdaptador.CitaViewHolder>() {

    class CitaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCitaIcono: ImageView = view.findViewById(R.id.ivCitaIcono)
        val tvCitaTitulo: TextView = view.findViewById(R.id.tvCitaTitulo)
        val tvCitaDetalle: TextView = view.findViewById(R.id.tvCitaDetalle)
        val tvCitaEstado: TextView = view.findViewById(R.id.tvCitaEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = listaCitas[position]

        holder.tvCitaTitulo.text = cita.motivo
        holder.tvCitaDetalle.text = cita.detalleFecha
        holder.tvCitaEstado.text = cita.estado

        // Si gustas puedes manejar iconos dinámicos o dejar el predeterminado del XML

        holder.itemView.setOnClickListener {
            onSeleccionarCita(cita)
        }
    }

    override fun getItemCount(): Int = listaCitas.size

    fun actualizarLista(nuevaLista: List<DataCita>) {
        listaCitas = nuevaLista
        notifyDataSetChanged()
    }
}