package com.example.notetask2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(var title:String,var content:String , @PrimaryKey(autoGenerate = true)
val id:Int =0,) : Parcelable
