package com.example.notetask2.ui.pre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notetask2.model.Note
import com.example.notetask2.model.NoteDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel  @Inject constructor(private val db: NoteDatabase): ViewModel()   {

    fun addNote( title:String,content:String){
        viewModelScope.launch {
            val note = Note(title=title, content = content)
            db.dao.addNote(note)

        }
    }

}