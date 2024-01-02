package com.ivzb.craftlog.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val content: String,
    val tags: List<String>,
    val date: Date,
    @Embedded val link: LinkEntity?,
    val additionalData: Map<String, String>
)
