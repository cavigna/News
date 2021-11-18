package com.example.news.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @SuppressLint("NewApi")
    fun fechaApi(fechaLoca: String): String{

        val inputDate= DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH)
        val fecha = LocalDate.parse(fechaLoca, inputDate)

        return outputFormatter.format(fecha)
    }

    fun formateameLaFecha(fechaLoca: String):Date?{
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val output = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = sdf.parse(fechaLoca)
        return date
    }
}
/*
    @SuppressLint("NewApi")
    fun fechaApi(fechaLoca: String):Date {
        //"fecha": "2021-11-04T03:00:00.000Z", 04-11-2021
        val inputDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH)
        val fecha = LocalDate.parse(fechaLoca, inputDate)

        return fecha.


    }
 */