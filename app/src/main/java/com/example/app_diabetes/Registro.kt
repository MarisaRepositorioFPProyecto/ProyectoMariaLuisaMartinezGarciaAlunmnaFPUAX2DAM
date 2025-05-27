package com.example.app_diabetes


data class Registro(
    val id: Int,
    val nivel: Float,
    val insulina: Float,
    val fecha: String,
    val hora: String,
    val notaLibre: String?
)
