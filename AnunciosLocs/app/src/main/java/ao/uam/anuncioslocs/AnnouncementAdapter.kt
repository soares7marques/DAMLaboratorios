package ao.uam.anuncioslocs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class AnnouncementAdapter : RecyclerView.Adapter<AnnouncementAdapter.ViewHolder>() {

    private var announcements = listOf<Announcement>()

    fun submitList(newList: List<Announcement>) {
        announcements = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_announcement, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(announcements[position])
    }

    override fun getItemCount() = announcements.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvSave: TextView = itemView.findViewById(R.id.tvSave)
        private val tvCancel: TextView = itemView.findViewById(R.id.tvCancel)

        fun bind(announcement: Announcement) {
            tvLocation.text = announcement.location
            tvTime.text = announcement.time
            tvTitle.text = announcement.title

            tvSave.setOnClickListener {
                Toast.makeText(itemView.context, "Anúncio guardado", Toast.LENGTH_SHORT).show()
            }

            tvCancel.setOnClickListener {
                itemView.visibility = View.GONE
            }
        }
    }
}

data class Announcement(
    val location: String,
    val time: String,
    val title: String
)