package com.ivzb.craftlog.data.mapper

import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.data.entity.NoteEntity

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        content = content,
        tags = tags,
        date = date,
        url = url,
        site = site,
        imageUrl = imageUrl
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id ?: 0L,
        content = content,
        tags = tags,
        date = date,
        url = url,
        site = site,
        imageUrl = imageUrl
    )
}
