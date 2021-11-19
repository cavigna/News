package com.example.news.utils

import android.annotation.SuppressLint
import java.time.Duration
import java.util.*
import kotlin.math.min

@SuppressLint("NewApi")
fun calcularDiferenciaTemporal(date: Date): String {

    val horaDeAhora = Calendar.getInstance().time

    //val diff = horaDeAhora.time - date.time
    val diff = Duration.between(date.toInstant(),horaDeAhora.toInstant())

    return when{
        diff.toHours()<24 -> "Hace ${diff.toHours()} horas"
        diff.toMinutes()<60 ->"Hace ${diff.toMinutes()} minutos"
        diff.toDays()<=1 -> "Hace ${diff.toDays()} día"
        else -> "Hace ${diff.toDays()} dias"
    }



}

/*
@SuppressLint("NewApi")
fun calcularDiferenciaDeHoras(date: Date): Long {

    val horaDeAhora = Calendar.getInstance().time

    //val diff = horaDeAhora.time - date.time
    val diff = Duration.between(date.toInstant(),horaDeAhora.toInstant())

    return diff.toMinutes()



}
 */
/*


fun calcularDiferenciaDeHoras(date: Date): Long {

    val horaDeAhora = Calendar.getInstance().time

    val diff = horaDeAhora.time - date.time

    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24


    return when {
        seconds<60 -> {
            seconds
        }
        minutes<60 -> {
            minutes
        }
        hours<24 -> {
            hours
        }
        else -> {
            days
        }
    }



}
 */