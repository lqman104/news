package com.luqman.news.core.helper

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {

    private const val SIMPLE_DATE = "dd-MM-yyyy"
    private const val DAY_DATE = "E, dd MMMM yyyy"
    private const val UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    fun Long?.toDate(toFormat: String = SIMPLE_DATE): String {
        return if (this == null || this == 0L) {
            ""
        } else {
            val simpleDateFormat = SimpleDateFormat(toFormat, Locale.getDefault())
            val date = Date(this)
            simpleDateFormat.format(date)
        }

    }

    fun String.toDate(toFormat: String = DAY_DATE): String {
            val input = SimpleDateFormat(UTC_PATTERN,Locale.getDefault())
            val simpleDateFormat = SimpleDateFormat(toFormat, Locale.getDefault())

            return try {
                val inputDate = input.parse(this)
                if (inputDate != null) {
                    simpleDateFormat.format(inputDate)
                } else {
                    ""
                }
            } catch (e: ParseException) {
                ""
            }
    }
}