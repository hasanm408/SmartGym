package com.example.smartgym.model

import java.io.Serializable

data class User(
    val name: String,
    val email: String = "",
    val password: String = "",
    val age: Int,
    val gender: String,
    var selectedTrainerName: String? = null
) : Serializable
