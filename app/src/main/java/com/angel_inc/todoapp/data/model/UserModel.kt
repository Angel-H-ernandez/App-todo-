package com.angel_inc.todoapp.data.model

import com.google.gson.annotations.SerializedName


data class UserModel(
    val id: Int,
    val username: String,
    val email: String,
    val password_hash: String,
    val created_at: String,
    val isPro: Boolean
)