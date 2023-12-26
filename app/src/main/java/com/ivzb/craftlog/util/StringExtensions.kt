package com.ivzb.craftlog.util

import android.util.Patterns

fun String.extractUrl(): String? =
    this
        .split(" ")
        .firstOrNull { Patterns.WEB_URL.matcher(it).find() }