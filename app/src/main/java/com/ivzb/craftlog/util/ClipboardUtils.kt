package com.ivzb.craftlog.util

import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager

fun isClipboardEnabled(clipboard: ClipboardManager): Boolean {
    return when {
        !clipboard.hasPrimaryClip() -> false
        clipboard.primaryClipDescription?.hasMimeType(MIMETYPE_TEXT_PLAIN) == false -> false
        else -> true
    }
}

fun ClipboardManager.getItem() = primaryClip?.getItemAt(0)?.text.toString()
