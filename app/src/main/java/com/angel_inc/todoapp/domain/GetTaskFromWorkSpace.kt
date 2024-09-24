package com.angel_inc.todoapp.domain

import android.util.Log
import com.angel_inc.todoapp.data.TaskRepository
import com.angel_inc.todoapp.data.model.TaskModel
import javax.inject.Inject

class GetTaskFromWorkSpace @Inject constructor(private val repository: TaskRepository) {

    suspend operator fun invoke(id: Int, status: Boolean) : Result<List<TaskModel>>{
        val task = repository.taskWorkSpace("eq."+ id, "eq."+status)
        Log.d("INFO use case", task.toString())
        return task
    }

}