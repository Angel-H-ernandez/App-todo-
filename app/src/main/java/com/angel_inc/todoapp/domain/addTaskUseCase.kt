package com.angel_inc.todoapp.domain

import android.util.Log
import com.angel_inc.todoapp.data.TaskRepository
import com.angel_inc.todoapp.data.model.TaskModel
import javax.inject.Inject

class addTaskUseCase @Inject constructor(private val repo: TaskRepository) {
    suspend operator fun invoke(nombre: String) : Result<List<TaskModel>> {
        val task = repo.addTask(nombre)
        Log.d("INFO use case", task.toString())
        return task
    }

}