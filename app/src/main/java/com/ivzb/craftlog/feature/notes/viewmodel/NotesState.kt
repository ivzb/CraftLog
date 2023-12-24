package com.ivzb.craftlog.feature.notes.viewmodel

import com.ivzb.craftlog.domain.model.Note

data class NotesState(
    val notes: List<Note> = emptyList()
)
