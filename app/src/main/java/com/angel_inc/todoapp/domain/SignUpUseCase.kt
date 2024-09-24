package com.angel_inc.todoapp.domain

import com.angel_inc.todoapp.data.UserRepository
import com.angel_inc.todoapp.data.model.UserModel
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(username: String, email: String, password: String): Result<UserModel>{
       return  repository.signUp(username, email, password);
    }
}