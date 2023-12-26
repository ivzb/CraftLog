package com.ivzb.craftlog.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Note(
    val id: Long?,
    val content: String,
    val tags: List<String>,
    val date: Date,
    val link: Link?,
) : Parcelable {

    override fun toString() = "$content (${tags.joinToString(", ")})"

}
