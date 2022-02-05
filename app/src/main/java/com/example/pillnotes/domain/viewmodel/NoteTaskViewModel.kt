package com.example.pillnotes.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pillnotes.domain.model.NoteTask
import com.example.pillnotes.presentation.interactor.NoteTaskInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteTaskViewModel @Inject constructor(
    private val repository: NoteTaskInteractor
) : ViewModel() {

    private val _noteTask = MutableLiveData<List<NoteTask>>()
    val noteTask: LiveData<List<NoteTask>> get() = _noteTask

    fun getAllTask() {
        viewModelScope.launch {
            repository.getAllNoteTask().collect { listNoteTask ->
                _noteTask.value = listNoteTask
            }
        }
    }

    fun addTask(noteTask: NoteTask) {
        viewModelScope.launch {
            repository.insertNoteTask(noteTask)
        }
    }

    fun deleteTask(noteTask: NoteTask) {
        viewModelScope.launch {
            repository.deleteNoteTask(noteTask)
        }
    }
}