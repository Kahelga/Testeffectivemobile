package com.example.testEffectiveMobile.util.validation

import android.util.Patterns

object InputValidator {

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank()
    }

    fun areInputsValid(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }
}