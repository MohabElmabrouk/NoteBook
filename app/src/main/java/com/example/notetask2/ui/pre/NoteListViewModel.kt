package com.example.notetask2.ui.pre

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetask2.model.Note
import com.example.notetask2.model.NoteDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel  @Inject constructor(private val db: NoteDatabase
): ViewModel()   {

    var Noteslist by mutableStateOf(emptyList<Note>())
        private set
    init {
        getNotes()
    }
    fun getNotes(){
        db.dao.getNotes().onEach{ notesList ->
            Noteslist= notesList
        }.launchIn(viewModelScope)

    }

    fun deleteNote(note:Note){
            viewModelScope.launch {
                db.dao.deleteNote(note)}
    }

    fun update(note: Note){
        viewModelScope.launch {
            db.dao.update(note)}
    }

//    fun deleteAllNotes(){
//        viewModelScope.launch {
//            db.dao.deleteAll()
//                getNotes()
//        }
//    }
    //onEach كل م تتغير القيمة تجيب القائمة كلها (مراقبة التغيرات)

}