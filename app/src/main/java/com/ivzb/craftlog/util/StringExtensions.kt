package com.ivzb.craftlog.util

import android.util.Patterns
import androidx.compose.ui.text.AnnotatedString

fun String.extractUrl(): String? =
    this
        .split(" ")
        .firstOrNull { Patterns.WEB_URL.matcher(it).find() }

fun AnnotatedString.Builder.appendLink(linkText: String, linkUrl: String) {
    pushStringAnnotation(tag = linkUrl, annotation = linkUrl)
    append(linkText)
    pop()
}

fun AnnotatedString.onLinkClick(offset: Int, onClick: (String) -> Unit) {
    getStringAnnotations(start = offset, end = offset).firstOrNull()?.let {
        onClick(it.item)
    }
}