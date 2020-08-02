package com.example.todoapp.data

import androidx.room.TypeConverter
import com.example.todoapp.data.models.Priority

class Converter {

    @TypeConverter
    fun fromPrior(priority: Priority): String{
        return priority.name
    }

    @TypeConverter
    fun toPrior(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}