package com.angel_inc.todoapp.data

import android.util.Log
import com.angel_inc.todoapp.data.model.TaskModel
import com.angel_inc.todoapp.data.network.ApiClient
import com.angel_inc.todoapp.data.network.TaskRequest
import javax.inject.Inject

class TaskRepository @Inject constructor(private val apiClient: ApiClient) {

    suspend fun taskWorkSpace(id: String, status: String) : Result<List<TaskModel>>{
        return try {
            //hacer la consulta
            val tasks = apiClient.getTaskWorkSpace(id, status)

            //manejar el resultado
            if(!tasks.isEmpty()){
                Log.d("INFO repository", tasks.toString())
                Result.success(tasks) // Obtener el primer usuario si hay usuarios
            }else{
                Result.failure(Exception("No task found"))
            }
        }catch (e: Exception   ){
            Log.e("INFO", "Task repository failed: ${e.message}")
            Result.failure(e)

        }
    }

    suspend fun addTask(name: String) : Result<List<TaskModel>>{
        return try {
            val newTask = TaskRequest(
                title = name,
                group_task_id = 3,
                assigned_user_id = 11,
                created_by_user_id = 11,
                completed = false,
            )
            val task  = apiClient.addTask(newTask)
            //hacer la consulta
            if(!task.isEmpty()){
                Log.d("INFO repository", task.toString())
                Result.success(task) // Obtener el primer usuario si hay usuarios
            }else{
                Result.failure(Exception("No task found"))
            }

        }catch (e: Exception   ){
            Log.e("INFO", "Task repository failed: ${e.message}")
            Result.failure(e)

        }
    }

    suspend fun completedTask(id: String, task: TaskModel) : Result<List<TaskModel>>{
        return try{
            val newTask = TaskRequest(
                title = task.title,
                group_task_id = task.group_task_id,
                assigned_user_id = task.assigned_user_id,
                created_by_user_id = task.created_by_user_id,
                completed = true,
            )
            val task = apiClient.completedTask(id, newTask)
            if(!task.isEmpty()){
                Log.d("INFO repository", task.toString())
                Result.success(task) // Obtener el primer usuario si hay usuarios
            }else{
                Result.failure(Exception("No task found"))
            }
        }catch (e: Exception){
            Log.e("INFO", "Task repository failed: ${e.message}")
            Result.failure(e)
        }

    }
}