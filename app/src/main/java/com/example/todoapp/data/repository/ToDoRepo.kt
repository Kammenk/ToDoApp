package com.example.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.example.todoapp.data.ToDoDAO
import com.example.todoapp.data.models.ToDoData

class ToDoRepo(private val toDoDAO: ToDoDAO) {

    val getAllData: LiveData<List<ToDoData>> = toDoDAO.getAllData()

    suspend fun insertData(toDoData: ToDoData){
        toDoDAO.insertData(toDoData)
    }
}