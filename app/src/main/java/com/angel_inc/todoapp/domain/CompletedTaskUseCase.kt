package com.angel_inc.todoapp.domain

import android.util.Log
import com.angel_inc.todoapp.data.TaskRepository
import com.angel_inc.todoapp.data.model.TaskModel
import javax.inject.Inject

class CompletedTaskUseCase @Inject constructor(private val repository: TaskRepository){
    suspend operator fun invoke(task: TaskModel) : Result<List<TaskModel>> {
        val taskNew = task.copy(completed = true)
        Log.d("INFO completed use case", taskNew.toString())
        val task = repository.completedTask("eq." + taskNew.id, taskNew)
        return task
    }
}