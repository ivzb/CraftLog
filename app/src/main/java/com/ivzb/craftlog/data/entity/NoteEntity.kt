package com.ivzb.craftlog.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val content: String,
    val tags: List<String>,
    val date: Date,
    val url: String?,
    val site: String?,
    val imageUrl: String?,
)
