package com.example.notetask2.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note: Note)

    @Query("select * from note")
     fun getNotes(): Flow<List<Note>>

     @Delete
     suspend fun deleteNote(note:Note)

     @Update
     suspend fun update(note:Note)

   //  @Query("")

//    @Query("DELETE FROM note")
//    suspend fun deleteAll():Int


}