package com.ivzb.craftlog.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Link(
    val url: String,
    val site: String? = null,
    val title: String? = null,
    val imageUrl: String? = null,
) : Parcelable