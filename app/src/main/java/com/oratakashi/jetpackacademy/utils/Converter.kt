package com.oratakashi.jetpackacademy.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Converter {
    @Throws(ParseException::class)
    fun dateFormat(date: String, input : String, output : String) : String{
        var format = SimpleDateFormat(input, Locale.getDefault())
        var newDate : Date? = null

        newDate = format.parse(date)

        format = SimpleDateFormat(output, Locale.getDefault())

        return format.format(newDate)
    }
}