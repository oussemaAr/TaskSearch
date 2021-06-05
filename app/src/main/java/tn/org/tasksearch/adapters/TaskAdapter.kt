package tn.org.tasksearch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.org.tasksearch.databinding.TaskItemBinding
import tn.org.tasksearch.search.TaskUIConfig

class TaskAdapter(
    private val data: List<TaskUIConfig>,
    val itemCLick: (TaskUIConfig) -> Unit
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    class ViewHolder(val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TaskItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.task = item
        holder.binding.root.setOnClickListener {
            itemCLick(item)
        }
    }


    override fun getItemCount() =
        data.size

}