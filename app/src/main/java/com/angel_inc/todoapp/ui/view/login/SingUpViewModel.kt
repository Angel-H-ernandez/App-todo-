package com.angel_inc.todoapp.ui.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angel_inc.todoapp.data.model.UserModel
import com.angel_inc.todoapp.domain.SignUpUseCase
import com.angel_inc.todoapp.ui.view.dependencias.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingUpViewModel @Inject constructor(val useCase: SignUpUseCase, private val validator: Validator): ViewModel() {

    // LiveData para observar el estado del inicio de sesión
    private val _signUpResult = MutableLiveData<Result<UserModel>>()
    val signUpResult: LiveData<Result<UserModel>> get() = _signUpResult

    // LiveData para manejar el estado de la carga (opcional)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun signUp(name: String, email: String, password: String, password2: String){
        viewModelScope.launch{
            _isLoading.value = true; //cargando
            if(!camposValidados(email, name, password, password)){
                _isLoading.value = false
                _signUpResult.value = Result.failure(Exception("LLene adecuadamente los campos")) // Comunica el error a la vista
                return@launch // Termina la función si la validación falla
            }
            try {
                val newUser= useCase(name, email, password);
                _signUpResult.value = newUser;

                // Puedes manejar el resultado aquí, por ejemplo, redirigir al usuario
                if (newUser.isSuccess) {
                    Log.i("INFO", "sigun successful: ${newUser.getOrNull()}")
                } else {
                    Log.e("INFO", "sigupfailed: ${newUser.exceptionOrNull()?.message}")
                }



            }catch (e: Exception) {

                Log.e("INFO", "Exception occurred during sign in: ${e.message}")
                _signUpResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false // Indica que la solicitud ha terminado
            }
        }
    }

    fun camposValidados(email: String, name: String, password: String, password2: String): Boolean {
        if(name.isBlank()) return false
        if(!validator.isEmail(email)) return false
        if(password.isBlank()) return false
        if(password!= password2) return false
        return true;

    }
}