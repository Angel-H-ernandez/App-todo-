package com.angel_inc.todoapp.ui.view.tareas

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angel_inc.todoapp.data.model.TaskModel
import com.angel_inc.todoapp.data.model.UserModel
import com.angel_inc.todoapp.domain.CompletedTaskUseCase
import com.angel_inc.todoapp.domain.GetTaskFromWorkSpace
import com.angel_inc.todoapp.domain.addTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TareasViewModel @Inject constructor(private val getTaskFromWorkSpace: GetTaskFromWorkSpace,
    private val addTaskUseCase: addTaskUseCase,
    private val completedTaskUseCase: CompletedTaskUseCase) : ViewModel() {

    //estateflow es mas modernos que live data
    private val _taskResult = MutableStateFlow<List<TaskModel>>(emptyList())
    val taskResult: StateFlow<List<TaskModel>> = _taskResult

    // LiveData para manejar el estado de la carga (opcional)
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init { //este metodo se llama acutomasticamente al llamar al viewmodel, como un oncreate de una vista
        Log.d("INFO", "hola")
        viewModelScope.launch{ //corrutina
            //marcar que esta cargando
            _isLoading.value = true;
            try {
                val result = getTaskFromWorkSpace(3, false) // mandar al caso de uso los datos
                _taskResult.value = result.getOrNull()!!;
                Log.i("INFO vieemodel", _taskResult.value.toString())
            }catch(e: Exception){
                Log.e("INFO", "Exception occurred during Get task: ${e.message}")
            }finally {
                _isLoading.value = false;
            }

        }
    }

    fun addTask(nombre: String){
        viewModelScope.launch{
            _isLoading.value = true
            try {
                val result = addTaskUseCase(nombre)
               // _taskResult.value = result.getOrNull()!!
                Log.i("INFO vieemodel", _taskResult.value.toString())
                Log.i("INFO vieemodel tarea nueva", result.toString())
                _taskResult.value += result.getOrNull()!!

            }catch(e: Exception){
                Log.e("INFO", "Exception occurred during Get task: ${e.message}")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun completedTask(task : TaskModel){
        viewModelScope.launch{
            try{

                val result = completedTaskUseCase(task)
                Log.i("INFO COMPLETED TASK", result.toString())
                _taskResult.value -= result.getOrNull()!!
                Log.i("INFO LIST TASK", _taskResult.value.toString())

                var idTaskCompleted = 0

                // Verifica si el resultado es exitoso
                result.onSuccess { tasks ->

                    if (tasks.isNotEmpty()) {
                        val firstTask = tasks.first() // Obtiene el primer elemento
                        idTaskCompleted = firstTask.id
                        _taskResult.value -= firstTask
                    } else {
                        Log.i("INFO", "La lista de tareas está vacía.")
                    }
                }.onFailure { exception ->
                    Log.e("ERROR", "Failed to complete task: ${exception.message}")
                }


                val listTaskModel = _taskResult.value?.filter { task ->
                    task.id != idTaskCompleted // Incluye solo las tareas cuyo id no sea 12
                } ?: emptyList() // Si _taskResult.value es nulo, crea una lista vacía

                _taskResult.value = listTaskModel


            }catch(e: Exception){
                Log.e("INFO", "Exception occurred during Get task: ${e.message}")
            }finally {
                _isLoading.value = false
            }
        }
    }


}