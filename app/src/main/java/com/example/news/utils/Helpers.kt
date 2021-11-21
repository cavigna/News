package com.example.news.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.min

@SuppressLint("NewApi")
fun calcularDiferenciaTemporal(date: Date): String {

    val horaDeAhora = Calendar.getInstance().time

    val sdf = SimpleDateFormat("dd-MM-yyyy")
    val diff = Duration.between(date.toInstant(),horaDeAhora.toInstant())


    return when{
        //diff.toMillis() < 6000  ->"Hace ${diff.toMillis()} segundos"
        diff.toMinutes() in 1..60 ->"Hace ${diff.toMinutes()} minutos"
        diff.toHours() in 1..23 -> "Hace ${diff.toHours()} horas"
        diff.toDays() in 1..1 -> "Hace ${diff.toDays()} día"
        diff.toDays()> 1 -> "Hace ${diff.toDays()} dias"
        else -> sdf.format(date)
    }



}

fun formateameLaFecha(fechaLoca: String): Date? {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    //val output = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = sdf.parse(fechaLoca)
    return date
}


@SuppressLint("NewApi")
fun fechaApi(fechaLoca: String): String{
    //"fecha": "2021-11-04T03:00:00.000Z", 04-11-2021
    val inputDate= DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH)
    val fecha = LocalDate.parse(fechaLoca, inputDate)

    return outputFormatter.format(fecha)
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