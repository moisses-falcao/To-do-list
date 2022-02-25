package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.TaskModelBinding
import com.example.todolist.localdatabase.TaskDao
import com.example.todolist.localdatabase.TaskDatabase
import com.example.todolist.model.TaskModel
import com.example.todolist.ui.MainActivity

class ListTaskAdapter() : RecyclerView.Adapter<ListTaskAdapter.ViewHolder>() {

    lateinit var taskList: List<TaskModel>

    var listenerEdit: (TaskModel) -> Unit = {}
    var listenerDelete: (TaskModel) -> Unit = {}

    inner class ViewHolder(val binding: TaskModelBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(taskModel: TaskModel) {
            binding.tvTitleTask.text = taskModel.title
            binding.tvDateHourTask.text = "${taskModel.date} ${taskModel.hour}"

            binding.ivMore.setOnClickListener {
                showPopup(taskModel)
            }
        }

        private fun showPopup(taskModel: TaskModel) {
            val more = binding.ivMore
            val popupMenu = PopupMenu(more.context, more)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    //R.id.action_edit -> listenerEdit(taskModel)
                    R.id.action_delete -> listenerDelete(taskModel)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TaskModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(taskList[position]) {
                bind(taskModel = taskList[position])
            }
        }
    }
    override fun getItemCount(): Int = taskList.size

}