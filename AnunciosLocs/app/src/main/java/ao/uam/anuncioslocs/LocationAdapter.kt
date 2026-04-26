package ao.uam.anuncioslocs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class LocationAdapter : ListAdapter<Location, LocationAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNome: TextView = itemView.findViewById(R.id.tvLocationName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNome.text = getItem(position).nome
    }

    class DiffCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(a: Location, b: Location) = a.nome == b.nome
        override fun areContentsTheSame(a: Location, b: Location) = a == b
    }
}