package com.github.klemenswiyanto.gymcomp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.klemenswiyanto.gymcomp.R
import com.github.klemenswiyanto.gymcomp.data.Gym
import kotlinx.android.synthetic.main.does_item.view.*

class todoAdapter : ListAdapter<Gym, todoAdapter.DoesHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Gym>() {
            override fun areItemsTheSame(oldItem: Gym, newItem: Gym): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Gym, newItem: Gym): Boolean {
                return oldItem.title == newItem.title && oldItem.reps == newItem.reps
                        && oldItem.sets == newItem.sets
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoesHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.does_item, parent, false)
        return DoesHolder(itemView)
    }

    override fun onBindViewHolder(holder: DoesHolder, position: Int) {
        val currentGym: Gym = getItem(position)

        holder.textViewTitle.text = currentGym.title
        holder.textViewDescription.text = currentGym.reps.toString()
        holder.textViewDueTime.text = currentGym.sets.toString()
        holder.textViewDueDate.text = currentGym.duedate
    }

    fun getDoesAt(position: Int): Gym {
        return getItem(position)
    }

    inner class DoesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewTitle: TextView = itemView.text_view_title
        var textViewDescription: TextView = itemView.text_view_description
        var textViewDueTime: TextView = itemView.text_view_duetime
        var textViewDueDate: TextView = itemView.text_view_duedate
    }

    interface OnItemClickListener {
        fun onItemClick(gym: Gym)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
