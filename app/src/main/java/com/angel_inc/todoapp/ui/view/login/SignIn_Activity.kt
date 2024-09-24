package com.angel_inc.todoapp.ui.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import com.angel_inc.todoapp.R
import com.angel_inc.todoapp.databinding.ActivitySignInBinding
import com.angel_inc.todoapp.ui.view.dependencias.PreferencesManager
import com.angel_inc.todoapp.ui.view.home_principal_fragments.homeActivity
import dagger.hilt.android.AndroidEntryPoint


import java.util.prefs.Preferences
import javax.inject.Inject


// SignInActivity.kt
@AndroidEntryPoint
class SignIn_Activity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding //binding
    private val singInViewModel by viewModels<SingInViewModel>() //asi se engancha el view model al ctivity

    @Inject
    lateinit var preferencesManager: PreferencesManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = preferencesManager.getId()
        if(user != null) goToHome()

        initListeners()
        initObservers() // Inicializar los observadores para LiveData
    }

    private fun initListeners() {
        binding.BtSignIn.setOnClickListener {
           loggin()
        }
        binding.TvSignUp.setOnClickListener {
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }
    }



    private fun loggin() {
        val email = binding.EtEmail.text.toString()
        val password = binding.EtPassword.text.toString()
        singInViewModel.signIn(email, password) //MANDAR LOS DATOS DE SESION AL VIEWMODE
    }

    private fun initObservers() {
        // Observa el resultado del inicio de sesión
        singInViewModel.signInResult.observe(this) { result ->
            if (result.isSuccess) {
                // Inicio de sesión exitoso
                val user = result.getOrNull()
                Log.i("INFO", "Login successful: $user")
                Log.i("INFO", user!!.id.toString())
                preferencesManager.saveId(user!!.id.toString())
                goToHome()

            } else {

                // Error en el inicio de sesión
                val exception = result.exceptionOrNull()
                Log.e("ERROR", "Login failed: ${exception?.message}")
                marcarErrorCampos()
                Toast.makeText(this, "Login failed: ${exception?.message}", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun marcarErrorCampos() {
        binding.EtEmail.setBackgroundResource(R.drawable.edit_text_error)
        binding.EtPassword.setBackgroundResource(R.drawable.edit_text_error)
    }

    private fun goToHome() {
        val intent = Intent(this, homeActivity::class.java)
        startActivity(intent)
        finish();
    }


}














