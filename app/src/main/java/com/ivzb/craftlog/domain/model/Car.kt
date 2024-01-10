package com.ivzb.craftlog.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Car(
    val id: Long?,
    val brand: String,
    val model: String,
    val year: Int,
    val date: Date,
    val additionalData: Map<String, String>
) : Parcelable {

    override fun toString() = "$brand $model ($year)"

}
