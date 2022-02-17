package com.example.pillnotes.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillnotes.domain.model.NoteTaskBase
import com.example.pillnotes.presentation.interactor.NoteTaskInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteTaskViewModel @Inject constructor(
    private val repository: NoteTaskInteractor
) : ViewModel() {

    private val _noteTask = MutableLiveData<List<NoteTaskBase>>()
    val noteTask: LiveData<List<NoteTaskBase>> get() = _noteTask

    init {
        getAllTask()
    }

    private fun getAllTask() {
        viewModelScope.launch {
            repository.getAllNoteTask().collect { listNoteTask ->
                _noteTask.value = listNoteTask
            }
        }
    }

    fun addTask(noteTask: List<NoteTaskBase>) {
        viewModelScope.launch {
            repository.insertNoteTask(noteTask[0])
        }
    }

    fun deleteTask(noteTask: NoteTaskBase) {
        viewModelScope.launch {
            repository.deleteNoteTask(noteTask)
        }
    }
}