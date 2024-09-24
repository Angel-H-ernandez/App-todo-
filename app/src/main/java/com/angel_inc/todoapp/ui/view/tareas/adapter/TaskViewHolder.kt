package com.angel_inc.todoapp.ui.view.tareas.adapter

import android.graphics.Paint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.angel_inc.todoapp.data.model.TaskModel
import com.angel_inc.todoapp.databinding.ItemTaskBinding

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {



    private val binding =  ItemTaskBinding.bind(view)

    fun render(task: TaskModel, completedTaskFunction: (TaskModel) -> Unit) {
        val context = binding.tvTask.context
        binding.CbTask.isChecked = false
        binding.tvTask.text = task.title



       
        binding.CbTask.setOnClickListener {

            completedTaskFunction(task)
        }

    }

}