package com.example.todoapp.fragments

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoData

class SharedViewModel(application: Application): AndroidViewModel(application) {

    val emptyDB: MutableLiveData<Boolean> = MutableLiveData(true)

    fun isDBEmpty(toDoData: List<ToDoData>){
        emptyDB.value = toDoData.isEmpty()
    }



    fun verifyData(title: String, description: String): Boolean{
        return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            return false
        } else !(title.isEmpty() || description.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return when(priority){
            "High Priority" -> {
                Priority.HIGH}
            "Medium Priority" -> {
                Priority.MEDIUM}
            "Low Priority" -> {
                Priority.LOW}
            else -> {
                Priority.LOW}
        }
    }

    fun parsePriorityToInt(priority: Priority): Int{
        return when(priority){
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }
}